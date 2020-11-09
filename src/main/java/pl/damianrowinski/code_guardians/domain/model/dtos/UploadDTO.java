package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.Data;
import pl.damianrowinski.code_guardians.validation.CerFile;

import java.util.HashMap;
import java.util.Map;

@Data
public class UploadDTO {

    @CerFile
    private String cert;
    private Map<String, String> files;
    private String out;

    public UploadDTO() {
        files = new HashMap<>();
    }
}
