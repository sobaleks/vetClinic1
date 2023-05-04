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
@Table(name = "pet")
@ToString(exclude = {"vetCardsList"})
@EqualsAndHashCode(exclude = {"vetCardsList"})
public class Pet {

    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_pet")
    @SequenceGenerator(name = "seq_id_pet", sequenceName = "pet_pet_id_seq",allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "breed")
    private String breed;

    @Column(name = "age")
    private int age;

    @Column(name = "status")
    private String status;

    @Column(name = "id_own")
    private int idOwn;

    @JsonManagedReference
    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER)
    private Set<VetCard> vetCardsList = new HashSet<>();

}
