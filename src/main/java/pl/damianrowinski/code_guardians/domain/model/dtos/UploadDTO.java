package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class UploadDTO {
    
    private String cert;
    private Map<String, String> files;
    private String out;

    public UploadDTO() {
        files = new HashMap<>();
    }
}
