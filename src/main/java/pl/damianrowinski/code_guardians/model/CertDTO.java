package pl.damianrowinski.code_guardians.model;

import lombok.Data;

@Data
public class CertDTO {
    private String commonName;
    private String organization;
    private String alg;
    private long uuid;
}
