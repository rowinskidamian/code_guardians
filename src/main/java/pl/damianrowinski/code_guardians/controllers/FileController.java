package pl.damianrowinski.code_guardians.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadResponseDTO;
import pl.damianrowinski.code_guardians.services.FileService;
import pl.damianrowinski.code_guardians.validation.FileNameValidator;
import pl.damianrowinski.code_guardians.validation.FileType;

import java.io.File;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public UploadResponseDTO upload(@RequestParam("fileName") MultipartFile fileName, @RequestParam String outputPath,
                                    @RequestParam("cert") MultipartFile certificate) throws Exception {

        //tutaj zrobić walidację każdego z przyjętych parametrów JSONa
        File outputFilePath = new File(outputPath)
                .getCanonicalFile();

        FileNameValidator fileValidator = new FileNameValidator();
        fileValidator.validFile(fileName.getOriginalFilename(), FileType.PDF);
        fileValidator.validFile(certificate.getOriginalFilename(), FileType.CER);

        long fileSize = fileName.getSize();
//        if (fileSize < 1_000_000) throw new FileSizeException("Plik powinien mieć przynajmniej 1mb.");

        return fileService.encryptAndSaveFile(fileName, outputFilePath, certificate);
    }
}
