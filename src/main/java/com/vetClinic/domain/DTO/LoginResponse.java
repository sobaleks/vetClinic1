package com.vetClinic.domain.DTO;

import com.vetClinic.domain.Owner;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private OwnerResponseDTO owner;

    public LoginResponse(String token, OwnerResponseDTO owner) {
        this.token = token;
        this.owner = owner;
    }

    // геттеры и сеттеры
}
