package com.vetClinic.utils;

import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.Pet;
import com.vetClinic.domain.VetCard;
import com.vetClinic.repository.OwnerRepository;
import com.vetClinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Date;

@Component
public class DtoMapper {

    static PetRepository petRepository;
    static OwnerRepository ownerRepository;

    @Autowired
    public DtoMapper(PetRepository petRepository, OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository= ownerRepository;
    }

    public static VetCard fromVetCardRequestDtoToVetCard(VetCardRequestDTO vetCardRequestDTO){
        VetCard vetCard = new VetCard();
        vetCard.setId(vetCardRequestDTO.getId());
        vetCard.setDiagnosis(vetCardRequestDTO.getDiagnosis());
        vetCard.setRecommendations(vetCardRequestDTO.getRecommendations());
        vetCard.setDate(new Date(System.currentTimeMillis()));
        vetCard.setPet(petRepository.findById(vetCardRequestDTO.getIdPet()).get());
        return vetCard;
    }

    public static Pet fromPetRequestDtoToPet(PetRequestDTO petRequestDTO){
        Pet pet = new Pet();
        pet.setId(petRequestDTO.getId());
        pet.setName(petRequestDTO.getName());
        pet.setBreed(petRequestDTO.getBreed());
        pet.setAge(petRequestDTO.getAge());
        pet.setStatus(petRequestDTO.getStatus());
        pet.setOwner(ownerRepository.findById(petRequestDTO.getIdOwn()).get());
        return pet;
    }
}
