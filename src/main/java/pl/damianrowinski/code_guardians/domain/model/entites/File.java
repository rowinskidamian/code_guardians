package pl.damianrowinski.code_guardians.domain.model.entites;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Entity
@Transactional
@Table(name = File.TABLE_NAME)
public class File {
    final static String TABLE_NAME = "files";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uuid;

    private String documentAlg;
    private LocalDateTime operationDateTime;
    private String filePath;
    private String userName;
}
