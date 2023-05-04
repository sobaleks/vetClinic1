package com.vetClinic.service;

import com.vetClinic.domain.Owners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vetClinic.repository.OwnersRepository;
import java.util.ArrayList;

@Service
public class OwnersService {

    OwnersRepository ownersRepository;

    @Autowired
    public OwnersService(OwnersRepository ownersRepository) {
        this.ownersRepository = ownersRepository;
    }

    public Owners getOwnersById(int id){
        return ownersRepository.findById(id).orElse(null);
    }

    public ArrayList<Owners> getAllOwners(){
        return (ArrayList<Owners>) ownersRepository.findAll();
    }

    public Owners createdOwner(Owners owners){
        return ownersRepository.save(owners);
    }

    public Owners updateOwners(Owners owners){
        return ownersRepository.save(owners);
    }

    public void deletedOwners(int id){
        ownersRepository.deleteById(id);
    }

    public void recodingConsultation(int id){


    }
}
