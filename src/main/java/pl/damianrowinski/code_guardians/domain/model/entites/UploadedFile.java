package pl.damianrowinski.code_guardians.domain.model.entites;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Entity
@Transactional
@Table(name = UploadedFile.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UploadedFile {
    final static String TABLE_NAME = "uploaded_files";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uuid;

    @Column(name = "document_alg")
    private String documentAlg;

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
}
