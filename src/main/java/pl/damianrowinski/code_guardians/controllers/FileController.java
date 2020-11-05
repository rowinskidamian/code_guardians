package pl.damianrowinski.code_guardians.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.damianrowinski.code_guardians.domain.model.dtos.CertificateDTO;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadResponseDTO;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadedFileDTO;
import pl.damianrowinski.code_guardians.services.CertificateService;
import pl.damianrowinski.code_guardians.services.FileEncryptionService;
import pl.damianrowinski.code_guardians.services.FileService;
import pl.damianrowinski.code_guardians.services.PdfEditService;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final CertificateService certificateService;
    private final PdfEditService pdfEditService;
    private final FileEncryptionService fileEncryptionService;

    @PostMapping("/upload")
    public UploadResponseDTO upload(@RequestParam("fileName") MultipartFile fileName, @RequestParam String outputPath,
                                    @RequestParam("cert") MultipartFile certificate) throws Exception {

        String tempPath = outputPath + "/temp/";

        long fileSize = fileName.getSize();
//        if (fileSize < 1_000_000) throw new FileSizeException("Plik powinien mieć przynajmniej 1mb.");

        String tempPdfPath = fileService.saveTempFileAndGetPath(fileName, tempPath);
        String savedCertificatePath = fileService.saveTempFileAndGetPath(certificate, tempPath);
        CertificateDTO certData = certificateService.getDataFromCert(savedCertificatePath);

        UploadedFileDTO uploadedFileData = fileService.saveDataToBase(tempPdfPath);
        copyDataFromBaseToDTO(certData, uploadedFileData);

        String editedPdfPath = pdfEditService.addDataToPdf
                (tempPdfPath, tempPath + "editedPdf.pdf", certData);
        String savedFilePath = fileEncryptionService.encryptFile
                (editedPdfPath, outputPath + "/" + fileName.getOriginalFilename(), savedCertificatePath);

        return new UploadResponseDTO(savedFilePath, fileName.getContentType(), fileSize);

    }

    private void copyDataFromBaseToDTO(CertificateDTO certData, UploadedFileDTO uploadedFileData) {
        certData.setAlg(uploadedFileData.getDocumentAlg());
        certData.setUuid(uploadedFileData.getUuid());
    }

}
