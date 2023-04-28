package com.vetClinic.service;

import com.vetClinic.domain.Pet;
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

        return petRepository.findById(id).orElse(null);
    }

    public ArrayList<Pet> getAllPets(){
        return (ArrayList<Pet>) petRepository.findAll();
    }

    public Pet createPet(Pet pet){
        return  petRepository.save(pet);
    }

    public Pet updatePet(Pet pet){
        return petRepository.save(pet);
    }

    public void deletePet(int id){
        petRepository.deleteById(id);
    }

    public ArrayList<Pet> getPetsByIdOwn(int idOwn){
        return  petRepository.getPetsByIdOwn(idOwn);
    }

    @Transactional
    public void recodingConsultation(int id){
         petRepository.recodingConsultation(id);
    }


}
