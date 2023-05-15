package com.vetClinic.service;

import com.sun.jdi.InternalException;
import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.VetCard;
import com.vetClinic.repository.OwnerRepository;
import com.vetClinic.repository.PetRepository;
import com.vetClinic.repository.VetCardRepository;
import com.vetClinic.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.vetClinic.repository.DoctorRepository;
import java.util.ArrayList;

import static com.vetClinic.utils.ExeptionMessage.OWNER_OR_DOCTOR_EXISTS;

@Service
public class DoctorService {
    DoctorRepository doctorRepository;

    PetRepository petRepository;

    VetCardRepository vetCardRepository;

    OwnerRepository ownerRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, PetRepository petRepository,
                         VetCardRepository vetCardRepository, OwnerRepository ownerRepository,
                         PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.petRepository = petRepository;
        this.vetCardRepository = vetCardRepository;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Doctor getDoctorById(int id){

        return doctorRepository.findById(id).orElseThrow();
    }

    public ArrayList<Doctor> getAllDoctors(){
        return (ArrayList<Doctor>) doctorRepository.findAll();
    }

    public Doctor createDoctor(Doctor doctor){
        if ((ownerRepository.findOwnerByLogin(doctor.getLogin()).orElse(null)) != null) {
            throw new InternalException(OWNER_OR_DOCTOR_EXISTS);
        }
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return  doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor){
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(int id){
        doctorRepository.deleteById(id);
    }

    @Transactional
    public void changeStatus(int id, String changeStatus){
        petRepository.changeStatus(id, changeStatus);
    }

    public VetCard createVetCardDoctor(VetCardRequestDTO vetCardRequestDTO){
        VetCard vetCard = DtoMapper.fromVetCardRequestDtoToVetCard(vetCardRequestDTO);
        return  vetCardRepository.save(vetCard);
    }
}
