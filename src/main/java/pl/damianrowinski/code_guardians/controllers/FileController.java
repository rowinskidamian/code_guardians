package pl.damianrowinski.code_guardians.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.damianrowinski.code_guardians.exception.FileSizeException;
import pl.damianrowinski.code_guardians.model.FileResource;
import pl.damianrowinski.code_guardians.services.FileService;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Value("${upload.folder}")
    private String filesFolder;

    @PostMapping("/upload")
    public FileResource upload(@RequestParam("fileName") MultipartFile fileName, @RequestParam String filePath) {
        String savedFile = fileService.save(fileName, filePath);
        long fileSize = fileName.getSize();

//        if (fileSize < 1_000_000) throw new FileSizeException("Plik powinien mieć przynajmniej 1mb.");

        return new FileResource(savedFile, filePath, fileName.getContentType(), fileSize);
    }

}
