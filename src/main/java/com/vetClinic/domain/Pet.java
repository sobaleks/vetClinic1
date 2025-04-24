package com.vetClinic.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "pet")
@ToString(exclude = {"vetCardsList", "owner"})
@EqualsAndHashCode(exclude = {"vetCardsList", "owner"})
public class Pet {

    @Id
    @Column(name = "pet_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_pet")
    @SequenceGenerator(name = "seq_id_pet", sequenceName = "pet_pet_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "name")
    @Pattern(regexp = "[A-z]*")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "breed")
    private String breed;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender; // MALE, FEMALE, UNKNOWN

    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight; //

    @Column(name = "passport", length = 20, unique = true)
    private String passport;

    @Transient
    private int age;

    @Column(name = "pet_image_base64", columnDefinition = "TEXT")  // TEXT для больших данных
    private String imageBase64;

    @Column(name = "status")
    private String status;

    @JsonManagedReference
    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER)
    private Set<VetCard> vetCardsList = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_own", nullable = false)
    private Owner owner;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();
}
