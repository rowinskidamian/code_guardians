package pl.damianrowinski.code_guardians.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.damianrowinski.code_guardians.exception.EmptyFileException;

import java.io.*;

@Service
@Slf4j
public class FileService {

    @Value("${upload.folder}")
    private String defaultUploadPath;

    public String save(MultipartFile fileName, String uploadPath) {
        if(fileName == null) throw new EmptyFileException("Plik nie może być pusty");

        String destinationPath = uploadPath +"\\"+ fileName.getOriginalFilename();

        File fileToSave = new File(destinationPath);

        try (InputStream is = fileName.getInputStream();
             OutputStream os = new FileOutputStream(fileToSave)){

            IOUtils.copy(is, os);
        } catch (IOException e) {
            log.error("Błąd przy próbie wgrania pliku.");
        }

        return fileName.getOriginalFilename();
    }
}
