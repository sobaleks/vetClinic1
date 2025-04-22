package com.vetClinic.domain.DTO;

import lombok.Data;

@Data
public class DoctorResponseDTO {

    private String name;
    private String email;
    private String specialization;
    private String telephoneNumber;
}
