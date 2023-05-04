package com.vetClinic.domain.DTO;

import lombok.Data;


@Data
public class VetCardRequestDTO {

    private int id;
    private String diagnosis;
    private String recommendations;
    private int idPet;

}
