package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDTO {
    private String name;
    private String uri;
    private String contentType;
    private long size;
}
