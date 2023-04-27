package domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_doctor")
    @SequenceGenerator(name = "seq_id_doctor", sequenceName = "doctor_doctor_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name= "doc_name")
    private String name;

    @Column(name= "doc_surname")
    private String surname;

    @Column(name= "specialization")
    private String specialization;

    @Column(name= "tel_numbet")
    private String telephoneNumber;

    @Column(name= "doc_ login")
    private String login;

    @Column(name= "doc_password")
    private String password;
}
