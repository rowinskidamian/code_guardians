package pl.damianrowinski.code_guardians.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final UploadedFileRepository fileRepository;
    private final ModelMapper modelMapper;
    private final CertificateService certificateService;
    private final PdfEditService pdfEditService;
    private final FileEncryptionService fileEncryptionService;

    public UploadResponseDTO encryptAndSaveFile(MultipartFile fileName, String outputPath, MultipartFile certificate)
            throws Exception {
        String tempPath = outputPath + "/temp/";

        String tempPdfPath = this.saveTempFileAndGetPath(fileName, tempPath);
        String savedCertificatePath = this.saveTempFileAndGetPath(certificate, tempPath);
        CertificateDTO certData = certificateService.getDataFromCert(savedCertificatePath);

        UploadedFileDTO uploadedFileData = this.saveDataToBase(tempPdfPath);
        certData.setAlg(uploadedFileData.getDocumentAlg());
        certData.setUuid(uploadedFileData.getUuid());

        String editedPdfPath = pdfEditService.addDataToPdf
                (tempPdfPath, tempPath + "editedPdf.pdf", certData);
        String savedFilePath = fileEncryptionService.encryptFile
                (editedPdfPath, outputPath + "/" + fileName.getOriginalFilename(), savedCertificatePath);

        return new UploadResponseDTO(savedFilePath, fileName.getContentType(), fileName.getSize());
    }

    private UploadedFileDTO saveDataToBase(String savedPdfPath) {

        UploadedFile dataToSaveToBase = new UploadedFile();
        dataToSaveToBase.setFilePath(savedPdfPath);
        dataToSaveToBase.setUserName("TEST USER");
        dataToSaveToBase.setDocumentAlg("TEST ALG");

        UploadedFile savedFileData = fileRepository.save(dataToSaveToBase);

        return modelMapper.map(savedFileData, UploadedFileDTO.class);
    }

    private String saveTempFileAndGetPath(MultipartFile fileName, String uploadPath) {
        if(fileName == null) throw new EmptyFileException("Plik nie może być pusty");

        File uploadPathDirs = new File(uploadPath);
        uploadPathDirs.mkdirs();

        String destinationPath = uploadPath + fileName.getOriginalFilename();

        File fileToSave = new File(destinationPath);

        try (InputStream is = fileName.getInputStream();
             OutputStream os = new FileOutputStream(fileToSave)){

            IOUtils.copy(is, os);
        } catch (IOException e) {
            log.error("Błąd przy próbie wgrania pliku.");
        }

        return destinationPath;
    }
}
