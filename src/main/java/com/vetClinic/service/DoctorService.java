package com.vetClinic.service;

import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.VetCard;
import com.vetClinic.repository.PetRepository;
import com.vetClinic.repository.VetCardRepository;
import com.vetClinic.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.vetClinic.repository.DoctorRepository;
import java.util.ArrayList;

@Service
public class DoctorService {
    DoctorRepository doctorRepository;

    PetRepository petRepository;

    VetCardRepository vetCardRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, PetRepository petRepository, VetCardRepository vetCardRepository) {
        this.doctorRepository = doctorRepository;
        this.petRepository = petRepository;
        this.vetCardRepository = vetCardRepository;
    }

    public Doctor getDoctorById(int id){

        return doctorRepository.findById(id).orElse(null);
    }

    public ArrayList<Doctor> getAllDoctors(){
        return (ArrayList<Doctor>) doctorRepository.findAll();
    }

    public Doctor createDoctor(Doctor doctor){
        return  doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor){
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(int id){
        doctorRepository.deleteById(id);
    }

    @Transactional
    public void change(int id, String changeStatus){
        petRepository.change(id, changeStatus);
    }

    public VetCard createVetCardDoctor(VetCardRequestDTO vetCardRequestDTO){
        VetCard vetCard = DtoMapper.fromVetCardRequestDtoToVetCard(vetCardRequestDTO);
        return  vetCardRepository.save(vetCard);
    }
}
