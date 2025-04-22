package com.vetClinic.domain.DTO;

import lombok.Data;


@Data
public class VetCardRequestDTO {

    private int id;
    private String diagnosis;
    private String recommendations;
    private String vaccination;
    private int idPet;
}
