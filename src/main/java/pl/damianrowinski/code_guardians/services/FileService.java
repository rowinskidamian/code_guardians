package pl.damianrowinski.code_guardians.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damianrowinski.code_guardians.domain.model.dtos.CertificateDTO;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadResponseDTO;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadedFileDTO;
import pl.damianrowinski.code_guardians.domain.model.entites.UploadedFile;
import pl.damianrowinski.code_guardians.domain.repositories.UploadedFileRepository;
import pl.damianrowinski.code_guardians.encryption.CertificateManager;
import pl.damianrowinski.code_guardians.encryption.FileEncryptor;
import pl.damianrowinski.code_guardians.exception.EmptyFileException;
import pl.damianrowinski.code_guardians.pdf.PdfEditor;

import javax.transaction.Transactional;
import java.io.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final CertificateManager certificateManager;
    private final FileEncryptor fileEncryptor;
    private final UploadedFileRepository fileRepository;
    private final ModelMapper modelMapper;
    private final PdfEditor pdfEditor;

    public UploadResponseDTO encryptAndSaveFile(File fileToSave, File outputPath, File certificateFilePath)
            throws Exception {
        File tempPath = new File(outputPath, "temp");
        File tempPdfPath = saveTempFileAndGetPath(fileToSave, tempPath);

        CertificateDTO certData = certificateManager.getDataFromCert(certificateFilePath);

        UploadedFileDTO uploadedFileData = saveDataToBase(tempPdfPath, fileToSave.getName());
        certData.setDocumentShortcut(uploadedFileData.getDocumentShortcut());
        certData.setUuid(uploadedFileData.getUuid());

        File editedPdfPath = pdfEditor.addDataToPdf(tempPdfPath, tempPath, certData);
        File savedFilePath = fileEncryptor.encryptFile
                (editedPdfPath, new File(outputPath, fileToSave.getName()), certificateFilePath);

        FileUtils.deleteDirectory(tempPath);

        return new UploadResponseDTO(savedFilePath.toString(), fileToSave.length());
    }


    private UploadedFileDTO saveDataToBase(File savedPdfPath, String fileName) {

        UploadedFile dataToSaveToBase = new UploadedFile();
        dataToSaveToBase.setFilePath(savedPdfPath.toString());
        dataToSaveToBase.setUserName("TEST USER");
        dataToSaveToBase.setDocumentShortcut(fileName);

        UploadedFile savedFileData = fileRepository.save(dataToSaveToBase);
        log.info("Database operation - saved: " + savedFileData);
        return modelMapper.map(savedFileData, UploadedFileDTO.class);
    }

    private File saveTempFileAndGetPath(File fileName, File uploadPath) {
        if(fileName == null) throw new EmptyFileException("File can not be empty.");
        uploadPath.mkdirs();

        File fileToSave = new File(uploadPath, fileName.getName());

        try (InputStream is = new BufferedInputStream(new FileInputStream(fileName));
             OutputStream os = new FileOutputStream(fileToSave)){
            IOUtils.copy(is, os);
        } catch (IOException e) {
            log.error("Exception during loading file.");
        }
        return fileToSave;
    }
}
