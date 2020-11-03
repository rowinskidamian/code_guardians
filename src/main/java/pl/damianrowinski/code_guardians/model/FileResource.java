package pl.damianrowinski.code_guardians.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResource {
    private String name;
    private String uri;
    private String contentType;
    private long size;
}
