package pl.damianrowinski.code_guardians.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadDTO;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadResponseDTO;
import pl.damianrowinski.code_guardians.services.FileService;

import javax.validation.Valid;
import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload/list")
    public List<UploadResponseDTO> uploadMulti(@Valid @RequestBody UploadDTO uploadDTO) throws Exception {
        Map<String, String> files = uploadDTO.getFiles();

        List<UploadResponseDTO> uploadResponseDTOList = new ArrayList<>();
        Collection<String> filesToEncrypt = files.values();
        for (String fileToEncrypt : filesToEncrypt) {
            File fileToSave = new File(fileToEncrypt);
            UploadResponseDTO uploadResponseDTO = fileService.encryptAndSaveFile
                    (fileToSave, new File(uploadDTO.getOut()), new File(uploadDTO.getCert()));
            uploadResponseDTOList.add(uploadResponseDTO);
        }
        return uploadResponseDTOList;
    }
}
