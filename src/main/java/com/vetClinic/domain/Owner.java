package com.vetClinic.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "owner")
@ToString(exclude = {"petsList"})
@EqualsAndHashCode(exclude = {"petsList"})
public class Owner {

    @Id
    @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_owner")
    @SequenceGenerator(name = "seq_id_owner", sequenceName = "owner_owner_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name ="name")
    private String name;

    @Column(name ="surname")
    private String surname;

    @Column(name ="email")
    private String email;

    @Column(name ="tel_number")
    private String telephoneNumber;

    @Column(name ="login")
    private String login;

    @Column(name ="password")
    private String password;

    @JsonManagedReference
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Pet> petsList = new HashSet<>();

}
