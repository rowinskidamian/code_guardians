package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.Data;
import pl.damianrowinski.code_guardians.validation.annotations.*;
import pl.damianrowinski.code_guardians.validation.types.FileSize;

import java.util.HashMap;
import java.util.Map;

@Data
public class UploadDTO {

    @CerFile
    private String cert;

    @PdfFiles
    @MinSize(FileSize.ONE_MB)
    @MaxSize(FileSize.TEN_MB)
    private Map<String, String> files;

    @OutFolder
    private String out;

    public UploadDTO() {
        files = new HashMap<>();
    }
}
