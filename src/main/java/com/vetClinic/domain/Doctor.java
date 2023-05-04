package com.vetClinic.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @Column(name = "doctor_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_doctor")
    @SequenceGenerator(name = "seq_id_doctor", sequenceName = "doctor_doctor_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name= "doc_name")
    private String name;

    @Column(name= "doc_surname")
    private String surname;

    @Column(name= "specialization")
    private String specialization;

    @Column(name= "tel_number")
    private String telephoneNumber;

    @Column(name= "doc_login")
    private String login;

    @Column(name= "doc_pas")
    private String password;
}
