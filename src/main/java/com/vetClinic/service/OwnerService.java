package com.vetClinic.service;

import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.Owner;
import com.vetClinic.domain.Pet;
import com.vetClinic.repository.PetRepository;
import com.vetClinic.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vetClinic.repository.OwnerRepository;
import java.util.ArrayList;

@Service
public class OwnerService {

    OwnerRepository ownerRepository;
    PetRepository petRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository,PetRepository petRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
    }

    public Owner getOwnerById(int id){
        return ownerRepository.findById(id).orElseThrow();
    }

    public ArrayList<Owner> getAllOwners(){
        return (ArrayList<Owner>) ownerRepository.findAll();
    }

    public Owner createdOwner(Owner owner){
        return ownerRepository.save(owner);
    }

    public Owner updateOwner(Owner owners){
        return ownerRepository.save(owners);
    }

    public void deletedOwner(int id){
        ownerRepository.deleteById(id);
    }

    public void recodingConsultation(int id){
    }

    public ArrayList<Pet> getPetsByIdOwn(int idOwn){
        return petRepository.getPetsByIdOwn(idOwn);
    }

}
