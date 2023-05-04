package com.vetClinic.utils;

import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.VetCard;
import com.vetClinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.Date;

@Component
public class DtoMapper {

    static PetRepository petRepository;

    @Autowired
    public DtoMapper(PetRepository petRepository) {
        this.petRepository = petRepository;
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
}
