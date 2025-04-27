package com.vetClinic.domain.DTO;

import lombok.Data;

@Data
public class DoctorResponseDTO {

    private String name;
    private String email;
    private String specialisation;
    private String telephoneNumber;
    private String description;
    private String hashTag;
    private String imageBase64;
}
