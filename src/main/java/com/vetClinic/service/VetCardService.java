package com.vetClinic.service;

import com.vetClinic.domain.VetCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vetClinic.repository.VetCardRepository;

import java.util.ArrayList;

@Service
public class VetCardService {

    VetCardRepository vetCardRepository;

    @Autowired
    public VetCardService(VetCardRepository vetCardRepository) {this.vetCardRepository = vetCardRepository;
    }

    public VetCard getVetCardById(int id){

        return vetCardRepository.findById(id).orElse(null);
    }

    public ArrayList<VetCard> getAllVetCard(){
        return (ArrayList<VetCard>) vetCardRepository.findAll();
    }

    public VetCard createVetCard(VetCard vetCard){
        return vetCardRepository.save(vetCard);
    }

    public VetCard updateVetCard(VetCard vetCard){
        return vetCardRepository.save(vetCard);
    }

    public void deleteVetCard(int id){
        vetCardRepository.deleteById(id);
    }
}
