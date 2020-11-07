package pl.damianrowinski.code_guardians.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadDTO;
import pl.damianrowinski.code_guardians.domain.model.dtos.UploadResponseDTO;
import pl.damianrowinski.code_guardians.services.FileService;
import pl.damianrowinski.code_guardians.validation.FileValidator;
import pl.damianrowinski.code_guardians.validation.FileType;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;
    private final FileValidator fileValidator;

    @PostMapping("/upload/list")
    public List<UploadResponseDTO> uploadMulti(@RequestBody UploadDTO uploadDTO) throws Exception {
        fileValidator.validFile(new File(uploadDTO.getCert()), FileType.CER);
        Map<String, String> files = uploadDTO.getFiles();

        return validAndEncryptPDFs(uploadDTO, files);
    }

    private List<UploadResponseDTO> validAndEncryptPDFs(UploadDTO uploadDTO, Map<String, String> files) throws Exception {
        List<UploadResponseDTO> uploadResponseDTOList = new ArrayList<>();

        Collection <String> filesToEncrypt = files.values();
        for (String fileToEncrypt : filesToEncrypt) {
            File fileToSave = new File(fileToEncrypt);
            fileValidator.validFile(fileToSave, FileType.PDF);

            UploadResponseDTO uploadResponseDTO = fileService.encryptAndSaveFile
                    (fileToSave, new File(uploadDTO.getOut()), new File(uploadDTO.getCert()));
            uploadResponseDTOList.add(uploadResponseDTO);
        }
        return uploadResponseDTOList;
    }
}
