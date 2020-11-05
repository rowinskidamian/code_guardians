package pl.damianrowinski.code_guardians.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.damianrowinski.code_guardians.domain.model.dtos.CertificateDTO;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadResponseDTO;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadedFileDTO;
import pl.damianrowinski.code_guardians.domain.model.entites.UploadedFile;
import pl.damianrowinski.code_guardians.domain.repositories.UploadedFileRepository;
import pl.damianrowinski.code_guardians.exception.EmptyFileException;

import javax.transaction.Transactional;
import java.io.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FileService {

    private final CertificateService certificateService;
    private final FileEncryptionService fileEncryptionService;
    private final UploadedFileRepository fileRepository;
    private final ModelMapper modelMapper;
    private final PdfEditService pdfEditService;

    public UploadResponseDTO encryptAndSaveFile(MultipartFile fileName, String outputPath, MultipartFile certificate)
            throws Exception {
        String tempPath = outputPath + "/temp/";

        String tempPdfPath = saveTempFileAndGetPath(fileName, tempPath);
        String savedCertificatePath = saveTempFileAndGetPath(certificate, tempPath);
        CertificateDTO certData = certificateService.getDataFromCert(savedCertificatePath);

        UploadedFileDTO uploadedFileData = saveDataToBase(tempPdfPath, fileName.getOriginalFilename());
        certData.setDocumentShortcut(uploadedFileData.getDocumentShortcut());
        certData.setUuid(uploadedFileData.getUuid());

        String editedPdfPath = pdfEditService.addDataToPdf
                (tempPdfPath, tempPath + "editedPdf.pdf", certData);
        String savedFilePath = fileEncryptionService.encryptFile
                (editedPdfPath, outputPath + "/" + fileName.getOriginalFilename(), savedCertificatePath);

//        clearTemp(tempPath);

        return new UploadResponseDTO(savedFilePath, fileName.getContentType(), fileName.getSize());
    }

    private void clearTemp(String tempPath) throws IOException {
        File tempDir = new File(tempPath);
        FileUtils.deleteDirectory(tempDir);
    }

    private UploadedFileDTO saveDataToBase(String savedPdfPath, String fileName) {

        UploadedFile dataToSaveToBase = new UploadedFile();
        dataToSaveToBase.setFilePath(savedPdfPath);
        dataToSaveToBase.setUserName("TEST USER");
        dataToSaveToBase.setDocumentShortcut(fileName);

        UploadedFile savedFileData = fileRepository.save(dataToSaveToBase);
        log.info("Database operation - saved: " + savedFileData);
        return modelMapper.map(savedFileData, UploadedFileDTO.class);
    }

    private String saveTempFileAndGetPath(MultipartFile fileName, String uploadPath) {
        if(fileName == null) throw new EmptyFileException("File can not be empty.");

        File uploadPathDirs = new File(uploadPath);
        uploadPathDirs.mkdirs();

        String destinationPath = uploadPath + fileName.getOriginalFilename();

        File fileToSave = new File(destinationPath);

        try (InputStream is = fileName.getInputStream();
             OutputStream os = new FileOutputStream(fileToSave)){

            IOUtils.copy(is, os);
        } catch (IOException e) {
            log.error("Exception during loading file.");
        }

        return destinationPath;
    }
}
