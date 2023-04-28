package com.vetClinic.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pet")
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



}
