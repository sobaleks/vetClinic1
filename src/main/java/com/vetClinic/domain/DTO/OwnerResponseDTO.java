package com.vetClinic.domain.DTO;

import com.vetClinic.domain.Pet;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class OwnerResponseDTO {

    private String name;
    private String surname;
    private String email;
    private String telephoneNumber;
    private String imageBase64;
    private Set<Pet> petsList = new HashSet<>();
}
