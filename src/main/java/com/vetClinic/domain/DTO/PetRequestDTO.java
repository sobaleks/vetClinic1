package com.vetClinic.domain.DTO;

import lombok.Data;

@Data
public class PetRequestDTO {

    private Integer id;
    private String name;
    private String breed;
    private int age;
    private String status;
    private int idOwn;



}
