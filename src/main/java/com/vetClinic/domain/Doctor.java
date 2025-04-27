package com.vetClinic.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @Column(name = "doctor_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_doctor")
    @SequenceGenerator(name = "seq_id_doctor", sequenceName = "doctor_doctor_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "full_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "specialisation")
    private String specialisation;

    @Column(name = "tel_number")
    private String telephoneNumber;

    @Column(name = "doc_login")
    private String login;

    @Column(name = "doc_pas")
    private String password;

    @Column(name = "image_base64", columnDefinition = "TEXT")  // TEXT для больших данных
    private String imageBase64;

    @Column(name = "description")
    private String description;

    @Column(name = "hash_tag")
    private String hashTag;

    @Column(name = "role")
    private String role;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "doctor", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DoctorSchedule> schedules = new HashSet<>();

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();
}
