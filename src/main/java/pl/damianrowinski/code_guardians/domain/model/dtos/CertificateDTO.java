package pl.damianrowinski.code_guardians.domain.model.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CertificateDTO {
    private String commonName;
    private String organization;
    private String documentShortcut;
    private UUID uuid;

    @Override
    public String toString() {
        return "Common Name: " + commonName + ", organization: " + organization + ", document shortcut: "
                + documentShortcut + ", uuid: " + uuid.toString();
    }
}
