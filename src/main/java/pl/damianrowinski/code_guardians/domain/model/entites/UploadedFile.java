package pl.damianrowinski.code_guardians.domain.model.entites;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Transactional
@Table(name = UploadedFile.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UploadedFile {
    final static String TABLE_NAME = "uploaded_files";


    @Column(name = "uuid", unique = true)
    private UUID uuid = UUID.randomUUID();

    @Column(name = "document_shortcut")
    private String documentShortcut;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shortcutId;

    @Column(name = "operation_date_time")
    private LocalDateTime operationDateTime;

    @Column(name = "file_path")
    private String filePath;

    @Column(name = "user_name")
    private String userName;

    @PrePersist
    public void addOperationDate() {
        operationDateTime = LocalDateTime.now();
    }
    @PostPersist
    public void setShortcut() {
        documentShortcut = documentShortcut + shortcutId;
    }
}
