package com.vetClinic.domain.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PetResponseDTO {
    private Integer id;
    private String name;
    private String type;
    private String breed;
    private LocalDate dateOfBirth;
    private String gender;
    private BigDecimal weight;
    private String passport;
    private String imageBase64;
    private String status;
    private int idOwn;
}
