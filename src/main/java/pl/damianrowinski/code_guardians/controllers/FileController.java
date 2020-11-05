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


    @PostMapping("/upload")
    public UploadResponseDTO upload(@RequestParam("fileName") MultipartFile fileName, @RequestParam String outputPath,
                                    @RequestParam("cert") MultipartFile certificate) throws Exception {

        //tutaj zrobić walidację każdego z przyjętych parametrów JSONa

        long fileSize = fileName.getSize();
//        if (fileSize < 1_000_000) throw new FileSizeException("Plik powinien mieć przynajmniej 1mb.");

        UploadResponseDTO uploadResponseDTO = fileService.encryptAndSaveFile(fileName, outputPath, certificate);

        return uploadResponseDTO;

    }


}
