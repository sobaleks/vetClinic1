package com.vetClinic.domain.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PetRequestDTO {

    private Integer id;
    private String name;
    private String type;
    private String breed;
    private LocalDate dateOfBirth;
    private int age;
    private String status;
    private int idOwn;
}
