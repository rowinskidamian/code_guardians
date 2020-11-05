package pl.damianrowinski.code_guardians.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.damianrowinski.code_guardians.model.CertDTO;
import pl.damianrowinski.code_guardians.model.FileResource;
import pl.damianrowinski.code_guardians.services.CertificateService;
import pl.damianrowinski.code_guardians.services.FileService;

import java.io.IOException;
import java.security.cert.CertificateException;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final CertificateService certificateService;

    @PostMapping("/upload")
    public FileResource upload(@RequestParam("fileName") MultipartFile fileName, @RequestParam String outputPath,
                               @RequestParam("cert") MultipartFile cert) throws IOException, CertificateException {

        String savedFile = fileService.save(fileName, outputPath);
        String savedCert = fileService.save(cert, outputPath);
        CertDTO certData = certificateService.getDataFromCert(outputPath+"\\"+savedCert);

        long fileSize = fileName.getSize();
//        if (fileSize < 1_000_000) throw new FileSizeException("Plik powinien mieć przynajmniej 1mb.");

        return new FileResource(savedFile, outputPath, fileName.getContentType(), fileSize);
    }

}
