package pl.damianrowinski.code_guardians.domain.model.entites;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Getter
@Setter
@ToString
@Transactional
@Table(name = User.TABLE_NAME)

public class User {

    final static String TABLE_NAME = "users";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;

}
