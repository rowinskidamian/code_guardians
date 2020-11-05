package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UploadedFileDTO {
    private UUID uuid;
    private String documentShortcut;
    private LocalDateTime operationDateTime;
    private String filePath;
    private String userName;
}
