package com.vetClinic.service;

import com.vetClinic.authorization.UserAccess;
import com.vetClinic.domain.DTO.OwnerResponseDTO;
import com.vetClinic.domain.Owner;
import com.vetClinic.domain.Pet;
import com.vetClinic.exeptions.DataAccessException;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.DoctorRepository;
import com.vetClinic.repository.OwnerRepository;
import com.vetClinic.repository.PetRepository;
import com.vetClinic.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OwnerService {

    OwnerRepository ownerRepository;
    PetRepository petRepository;
    DoctorRepository doctorRepository;
    PasswordEncoder passwordEncoder;
    UserAccess userAccess;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, PetRepository petRepository,
                        DoctorRepository doctorRepository, PasswordEncoder passwordEncoder,
                        UserAccess userAccess) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAccess = userAccess;
    }


    public OwnerResponseDTO getOwnerById(int id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found"));
        userAccess.adminOrUserAuthorization(id);
        return DtoMapper.fromOwnerToOwnerResponseDTO(owner);
    }

    public ArrayList<OwnerResponseDTO> getAllOwners() {
        ArrayList<Owner> owners = (ArrayList<Owner>) ownerRepository.findAll();
        ArrayList<OwnerResponseDTO> ownerResponseDTO = new ArrayList<>();
        if (owners.isEmpty()) {
            throw new ObjectNotFoundException("don't find users");
        }
        for (Owner o : owners) {
            ownerResponseDTO.add(DtoMapper.fromOwnerToOwnerResponseDTO(o));
        }
        return ownerResponseDTO;
    }

    public Owner createOwner(Owner owner) {
        if (ownerRepository.findOwnerByLogin(owner.getLogin()).isPresent()) {
            throw new DataAccessException("User with name " + owner.getLogin() + " already exists");
        }
        if (owner.getImageBase64() != null
                && owner.getImageBase64().length() > 1_000_000) {
            throw new IllegalArgumentException("Фото слишком большое! Максимум 1 МБ.");
        }
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        owner.setRole("USER");
        return ownerRepository.save(owner);
    }

    public Owner updateOwner(Owner owner) {
        ownerRepository.findById(owner.getId()).orElseThrow(
                () -> new ObjectNotFoundException("User with id " + owner.getId() + " not found"));
        if (owner.getImageBase64() != null
                && owner.getImageBase64().length() > 1_000_000) {
            throw new IllegalArgumentException("Фото слишком большое! Максимум 1 МБ.");
        }
        owner.setPassword(passwordEncoder.encode(owner.getPassword()));
        owner.setRole("USER");
        userAccess.adminOrUserAuthorization(owner.getId());
        return ownerRepository.save(owner);
    }

    public void deleteOwner(int id) {
        ownerRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("User with id " + id + " not found"));
        userAccess.adminOrUserAuthorization(id);
        ownerRepository.deleteById(id);
    }

    public ArrayList<Pet> getPetsByIdOwn(int idOwn) {
        ownerRepository.findById(idOwn).orElseThrow(
                () -> new ObjectNotFoundException("User with id " + idOwn + " not found"));
        ArrayList<Pet> pets = petRepository.getPetsByIdOwn(idOwn);
        if (pets.isEmpty()) {
            throw new ObjectNotFoundException("Users not has pets");
        }
        userAccess.allUserAuthorization(idOwn);
        return petRepository.getPetsByIdOwn(idOwn);
    }

    @Transactional
    public ArrayList<OwnerResponseDTO> getOwnersNeedConsultation() {
        ArrayList<Owner> owners = ownerRepository.findPetsNeedConsultation();
        ArrayList<OwnerResponseDTO> ownerResponseDTO = new ArrayList<>();
        if (owners.isEmpty()) {
            throw new ObjectNotFoundException("No users on reception");
        }
        for (Owner o : owners) {
            ownerResponseDTO.add(DtoMapper.fromOwnerToOwnerResponseDTO(o));
        }
        userAccess.adminAuthorization();
        return ownerResponseDTO;
    }
}
