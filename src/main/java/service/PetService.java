package service;

import domain.Pet;
import org.springframework.stereotype.Service;
import repository.PetRepository;

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

}
