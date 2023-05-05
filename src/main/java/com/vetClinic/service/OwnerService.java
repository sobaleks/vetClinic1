package com.vetClinic.service;

import com.vetClinic.domain.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vetClinic.repository.OwnerRepository;
import java.util.ArrayList;

@Service
public class OwnerService {

    OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner getOwnerById(int id){
        return ownerRepository.findById(id).orElse(null);
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
}
