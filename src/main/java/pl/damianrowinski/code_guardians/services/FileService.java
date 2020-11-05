package pl.damianrowinski.code_guardians.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    public UploadedFileDTO saveDataToBase(String savedPdfPath) {

        UploadedFile dataToSaveToBase = new UploadedFile();
        dataToSaveToBase.setFilePath(savedPdfPath);
        dataToSaveToBase.setUserName("TEST USER");
        dataToSaveToBase.setDocumentAlg("TEST ALG");

        UploadedFile savedFileData = fileRepository.save(dataToSaveToBase);

        return modelMapper.map(savedFileData, UploadedFileDTO.class);
    }

    public String saveTempFileAndGetPath(MultipartFile fileName, String uploadPath) {
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
