package com.vetClinic.domain.DTO;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String password;
}
