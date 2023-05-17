package com.vetClinic.service;

import com.sun.jdi.InternalException;
import com.vetClinic.authorization.UserAccess;
import com.vetClinic.domain.DTO.DoctorResponseDTO;
import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.VetCard;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.DoctorRepository;
import com.vetClinic.repository.OwnerRepository;
import com.vetClinic.repository.PetRepository;
import com.vetClinic.repository.VetCardRepository;
import com.vetClinic.utils.DtoMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.vetClinic.utils.ExceptionMessage.OWNER_OR_DOCTOR_EXISTS;

@Service
public class DoctorService {
    DoctorRepository doctorRepository;

    PetRepository petRepository;

    VetCardRepository vetCardRepository;

    OwnerRepository ownerRepository;

    PasswordEncoder passwordEncoder;

    UserAccess userAccess;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, PetRepository petRepository,
                         VetCardRepository vetCardRepository, OwnerRepository ownerRepository,
                         PasswordEncoder passwordEncoder, UserAccess userAccess) {
        this.doctorRepository = doctorRepository;
        this.petRepository = petRepository;
        this.vetCardRepository = vetCardRepository;
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAccess = userAccess;
    }

    public DoctorResponseDTO getDoctorById(int id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Doctor with id " + id + " not found"));
        userAccess.doctorAuthorization(id);
        return DtoMapper.fromDoctorToDoctorResponseDTO(doctor);
    }

    public ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> doctors = (ArrayList<Doctor>) doctorRepository.findAll();
        ArrayList<DoctorResponseDTO> doctorResponseDTO = new ArrayList<>();
        if (doctors.isEmpty()) {
            throw new ObjectNotFoundException("don't find doctors");
        }
        for (Doctor d : doctors) {
            doctorResponseDTO.add(DtoMapper.fromDoctorToDoctorResponseDTO(d));
        }
        return doctors;
    }

    public Doctor createDoctor(Doctor doctor) {
        if ((ownerRepository.findOwnerByLogin(doctor.getLogin()).orElse(null)) != null) {
            throw new InternalException(OWNER_OR_DOCTOR_EXISTS);
        }
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctor.setRole("DOCTOR");
        userAccess.adminAuthorization();
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        doctorRepository.findById(doctor.getId()).orElseThrow(
                () -> new ObjectNotFoundException("Doctor with id " + doctor.getId() + " not found"));
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        userAccess.doctorAuthorization(doctor.getId());
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(int id) {
        doctorRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Doctor with id " + id + " not found"));
        userAccess.doctorAuthorization(id);
        doctorRepository.deleteById(id);
    }

    @Transactional
    public void changeStatus(int id, String changeStatus) {
        petRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Pet with id " + id + " not found"));
        userAccess.adminOrDoctorAuthorization();
        petRepository.changeStatus(id, changeStatus);
    }

    public VetCard createVetCardDoctor(VetCardRequestDTO vetCardRequestDTO) {
        VetCard vetCard = DtoMapper.fromVetCardRequestDtoToVetCard(vetCardRequestDTO);
        userAccess.adminOrDoctorAuthorization();
        return vetCardRepository.save(vetCard);
    }
}
