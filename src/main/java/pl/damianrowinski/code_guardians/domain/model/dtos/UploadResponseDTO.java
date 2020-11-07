package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadResponseDTO {
    private String savedFilePath;
    private long size;
}
