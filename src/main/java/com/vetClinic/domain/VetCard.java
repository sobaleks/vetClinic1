package com.vetClinic.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.sql.Date;
@Data
@Entity
@Table(name ="vet_card")
@ToString(exclude = {"pet"})
@EqualsAndHashCode(exclude = {"pet"})
public class VetCard {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_vet_card")
    @SequenceGenerator(name = "seq_id_vet_card", sequenceName = "vet_card_card_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "date")
    private Date date;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false)
    private Pet pet;

    @Column(name = "recommendations")
    private String recommendations;
}
