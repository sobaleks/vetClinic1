package com.vetClinic.service;

import com.vetClinic.authorization.UserAccess;
import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.VetCard;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.VetCardRepository;
import com.vetClinic.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class VetCardService {

    VetCardRepository vetCardRepository;
    UserAccess userAccess;

    @Autowired
    public VetCardService(VetCardRepository vetCardRepository, UserAccess userAccess) {
        this.vetCardRepository = vetCardRepository;
        this.userAccess = userAccess;
    }

    public VetCard getVetCardById(int id) {
        VetCard vetCard = vetCardRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("VetCard with id " + id + " not found"));
        userAccess.allUserAuthorization(vetCard.getPet().getOwner().getId());
        return vetCard;
    }

    public ArrayList<VetCard> getAllVetCard() {
        ArrayList<VetCard> vetCards = (ArrayList<VetCard>) vetCardRepository.findAll();
        if (vetCards.isEmpty()) {
            throw new ObjectNotFoundException("don't find vetCards");
        }
        userAccess.adminAuthorization();
        return vetCards;
    }

    public VetCard createVetCard(VetCardRequestDTO vetCardRequestDTO) {
        VetCard vetCard = DtoMapper.fromVetCardRequestDtoToVetCard(vetCardRequestDTO);
        userAccess.adminOrDoctorAuthorization();
        return vetCardRepository.save(vetCard);
    }

    public VetCard updateVetCard(VetCardRequestDTO vetCardRequestDTO) {
        vetCardRepository.findById(vetCardRequestDTO.getId()).orElseThrow(
                () -> new ObjectNotFoundException("VetCard with id " + vetCardRequestDTO.getId() + " not found"));
        userAccess.adminOrDoctorAuthorization();
        VetCard vetCard = DtoMapper.fromVetCardRequestDtoToVetCard(vetCardRequestDTO);
        return vetCardRepository.save(vetCard);
    }

    public void deleteVetCard(int id) {
        vetCardRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("VetCard with id " + id + " not found"));
        userAccess.adminOrDoctorAuthorization();
        vetCardRepository.deleteById(id);
    }
}
