package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.Data;

@Data
public class CertificateDTO {
    private String commonName;
    private String organization;
    private String alg;
    private long uuid;
}
