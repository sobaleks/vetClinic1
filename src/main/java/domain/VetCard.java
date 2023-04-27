package domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
@Data
@Entity
@Table(name ="vet_card")
public class VetCard {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_id_vet_card")
    @SequenceGenerator(name = "seq_id_vet_card", sequenceName = "vet_card_card_id_seq", allocationSize = 1)
    private int id;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "date")
    private Date date;

    @Column(name = "id_pet")
    private int idPet;

}
