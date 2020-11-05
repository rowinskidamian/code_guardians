package pl.damianrowinski.code_guardians.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

//@Entity
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long uuid;

    private String documentAlg;
    private LocalDateTime operationDateTime;
    private String filePath;
    private String userName;
}
