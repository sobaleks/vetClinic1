package domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "owners")
public class Owners {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_owners")
    @SequenceGenerator(name = "seq_id_owners", sequenceName = "owners_owner_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name ="surname")
    private String surname;

    @Column(name ="email")
    private String eMail;

    @Column(name ="telephoneNumber")
    private String telephoneNumber;

    @Column(name ="login")
    private String login;

    @Column(name ="password")
    private String password;

}
