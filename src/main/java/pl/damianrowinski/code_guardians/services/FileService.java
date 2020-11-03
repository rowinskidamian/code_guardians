package pl.damianrowinski.code_guardians.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Value("${upload.folder}")
    private String uploadPath;

    public String save(MultipartFile fileName) {
        return null;
    }
}
