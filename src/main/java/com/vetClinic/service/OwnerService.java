package com.vetClinic.service;

import com.sun.jdi.InternalException;
import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.Owner;
import com.vetClinic.domain.Pet;
import com.vetClinic.repository.DoctorRepository;
import com.vetClinic.repository.PetRepository;
import com.vetClinic.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.vetClinic.repository.OwnerRepository;

import java.util.ArrayList;
import java.util.Optional;

import static com.vetClinic.utils.ExeptionMessage.OWNER_OR_DOCTOR_EXISTS;

@Service
public class OwnerService {

    OwnerRepository ownerRepository;
    PetRepository petRepository;
    DoctorRepository doctorRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, PetRepository petRepository,
                        DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Owner getOwnerById(int id) {
       // String ownerLogin = SecurityContextHolder.getContext().getAuthentication().getName(); // Who authenticated!
        return ownerRepository.findById(id).orElseThrow();
    }

    public ArrayList<Owner> getAllOwners() {
        return (ArrayList<Owner>) ownerRepository.findAll();
    }

    public Owner createOwner(Owner owner) {
        if ((doctorRepository.findDoctorByLogin(owner.getLogin()).orElse(null)) != null) {
            throw new InternalException(OWNER_OR_DOCTOR_EXISTS);
        }
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        owner.setRole("USER");
        return ownerRepository.save(owner);
    }

    public Owner updateOwner(Owner owner) {
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        return ownerRepository.save(owner);
    }

    public void deleteOwner(int id) {
        ownerRepository.deleteById(id);
    }

    public void recodingConsultation(int id) {
    }

    public Optional<Owner> findOwnerByLastName(String ln) {
        return ownerRepository.findOwnerByLogin(ln);
    }

    public ArrayList<Pet> getPetsByIdOwn(int idOwn) {
        return petRepository.getPetsByIdOwn(idOwn);
    }

}
