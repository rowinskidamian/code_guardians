package pl.damianrowinski.code_guardians.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Slf4j
public class FileService {

    @Value("${upload.folder}")
    private String uploadPath;

    public String save(MultipartFile fileName) {

        File fileToSave = new File(uploadPath + fileName.getOriginalFilename());

        try (InputStream is = fileName.getInputStream();
             OutputStream os = new FileOutputStream(fileToSave)){

            IOUtils.copy(is, os);
        } catch (IOException e) {
            log.error("Błąd przy próbie wgrania pliku.");
        }

        return null;
    }
}
