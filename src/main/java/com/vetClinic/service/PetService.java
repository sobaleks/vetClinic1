package com.vetClinic.service;

import com.vetClinic.authorization.UserAccess;
import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.Pet;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.PetRepository;
import com.vetClinic.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PetService {

    PetRepository petRepository;
    UserAccess userAccess;

    public PetService(PetRepository petRepository, UserAccess userAccess) {
        this.petRepository = petRepository;
        this.userAccess = userAccess;
    }

    public Pet getPetById(int id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Pet with id " + id + " not found"));
        userAccess.allUserAuthorization(pet.getOwner().getId());
        return pet;
    }

    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> pets = (ArrayList<Pet>) petRepository.findAll();
        if (pets.isEmpty()) {
            throw new ObjectNotFoundException("don't find pets");
        }
        return pets;
    }

    public Pet createPet(PetRequestDTO petRequestDTO) {
        Pet pet = DtoMapper.fromPetRequestDtoToPet(petRequestDTO);
        userAccess.adminOrUserAuthorization(petRequestDTO.getIdOwn());
        return petRepository.save(pet);
    }

    public Pet updatePet(PetRequestDTO petRequestDTO) {
        petRepository.findById(petRequestDTO.getId()).orElseThrow(
                () -> new ObjectNotFoundException("Pet with id " + petRequestDTO.getId() + " not found"));
        Pet pet = DtoMapper.fromPetRequestDtoToPet(petRequestDTO);
        userAccess.adminOrUserAuthorization(petRequestDTO.getIdOwn());
        return petRepository.save(pet);
    }

    public void deletePet(int id) {
        petRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Pet with id " + id + " not found"));
        userAccess.adminAuthorization();
        petRepository.deleteById(id);
    }


    @Transactional
    public void recodingConsultation(String name) {
        Pet pet = petRepository.findPetByName(name).orElseThrow(
                () -> new ObjectNotFoundException("Pet with id " + name + " not found"));
        userAccess.adminOrUserAuthorization(pet.getOwner().getId());
        petRepository.recodingConsultation(pet.getName());
    }
}
