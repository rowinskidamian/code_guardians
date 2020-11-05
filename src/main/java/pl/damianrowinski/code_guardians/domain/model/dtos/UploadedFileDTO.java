package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UploadedFileDTO {
    private long uuid;
    private String documentAlg;
    private LocalDateTime operationDateTime;
    private String filePath;
    private String userName;
}
