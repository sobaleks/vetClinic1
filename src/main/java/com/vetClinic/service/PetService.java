package com.vetClinic.service;

import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.Pet;
import com.vetClinic.domain.VetCard;
import com.vetClinic.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.vetClinic.repository.PetRepository;
import java.util.ArrayList;

@Service
public class PetService {

    PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet getPetById(int id){
        return petRepository.findById(id).orElseThrow();
    }

    public ArrayList<Pet> getAllPets(){
        return (ArrayList<Pet>) petRepository.findAll();
    }

    public Pet createPet(PetRequestDTO petRequestDTO){
        Pet pet = DtoMapper.fromPetRequestDtoToPet(petRequestDTO);
        return petRepository.save(pet);
    }

    public Pet updatePet(PetRequestDTO petRequestDTO){
        Pet pet = DtoMapper.fromPetRequestDtoToPet(petRequestDTO);
        return petRepository.save(pet);
    }

    public void deletePet(int id){
        petRepository.deleteById(id);
    }


    @Transactional
    public void recodingConsultation(int id){
         petRepository.recodingConsultation(id);
    }




}
