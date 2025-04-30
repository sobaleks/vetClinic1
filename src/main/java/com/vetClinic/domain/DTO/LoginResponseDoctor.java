package com.vetClinic.domain.DTO;

import lombok.Data;

@Data
public class LoginResponseDoctor {
    private String token;
    private DoctorResponseDTO doctor;

    public LoginResponseDoctor(String token, DoctorResponseDTO doctor) {
        this.token = token;
        this.doctor = doctor;
    }
}
