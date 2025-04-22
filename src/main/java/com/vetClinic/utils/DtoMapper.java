package com.vetClinic.utils;

import com.vetClinic.domain.Appointment;
import com.vetClinic.domain.DTO.AppointmentRequestDTO;
import com.vetClinic.domain.DTO.AppointmentResponseDTO;
import com.vetClinic.domain.DTO.DoctorResponseDTO;
import com.vetClinic.domain.DTO.DoctorScheduleRequestDTO;
import com.vetClinic.domain.DTO.OwnerResponseDTO;
import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.DTO.VetCardRequestDTO;
import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.DoctorSchedule;
import com.vetClinic.domain.Owner;
import com.vetClinic.domain.Pet;
import com.vetClinic.domain.VetCard;
import com.vetClinic.repository.DoctorRepository;
import com.vetClinic.repository.OwnerRepository;
import com.vetClinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class DtoMapper {
    static DoctorRepository doctorRepository;
    static PetRepository petRepository;
    static OwnerRepository ownerRepository;

    @Autowired
    public DtoMapper(PetRepository petRepository, OwnerRepository ownerRepository,
                     DoctorRepository doctorRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
        this.doctorRepository = doctorRepository;
    }

    public static VetCard fromVetCardRequestDtoToVetCard(VetCardRequestDTO vetCardRequestDTO) {
        VetCard vetCard = new VetCard();
        vetCard.setId(vetCardRequestDTO.getId());
        vetCard.setDiagnosis(vetCardRequestDTO.getDiagnosis());
        vetCard.setRecommendations(vetCardRequestDTO.getRecommendations());
        vetCard.setDate(new Date(System.currentTimeMillis()));
        vetCard.setPet(petRepository.findById(vetCardRequestDTO.getIdPet()).get());
        return vetCard;
    }

    public static Pet fromPetRequestDtoToPet(PetRequestDTO petRequestDTO) {
        Pet pet = new Pet();
        pet.setId(petRequestDTO.getId());
        pet.setName(petRequestDTO.getName());
        pet.setBreed(petRequestDTO.getBreed());
        pet.setAge(petRequestDTO.getAge());
        pet.setStatus(petRequestDTO.getStatus());
        pet.setOwner(ownerRepository.findById(petRequestDTO.getIdOwn()).get());
        return pet;
    }

    public static OwnerResponseDTO fromOwnerToOwnerResponseDTO(Owner owner) {
        OwnerResponseDTO ownerResponseDTO = new OwnerResponseDTO();
        ownerResponseDTO.setName(owner.getName());
        ownerResponseDTO.setSurname(owner.getSurname());
        ownerResponseDTO.setEmail(owner.getEmail());
        ownerResponseDTO.setTelephoneNumber(owner.getTelephoneNumber());
        ownerResponseDTO.setPetsList(owner.getPetsList());
        return ownerResponseDTO;
    }

    public static DoctorResponseDTO fromDoctorToDoctorResponseDTO(Doctor doctor) {
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setName(doctor.getName());
        doctorResponseDTO.setSurname(doctor.getSurname());
        doctorResponseDTO.setTelephoneNumber(doctor.getTelephoneNumber());
        doctorResponseDTO.setSpecialization(doctor.getSpecialization());
        return doctorResponseDTO;
    }

    public static Appointment fromAppointmentRequestDtoToAppointment(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment();

        // Базовые поля
        appointment.setId(dto.getId());
        appointment.setDateTime(dto.getDateTime());
        appointment.setDurationMinutes(dto.getDurationMinutes());
        appointment.setStatus(dto.getStatus());

        // Связанные сущности
        appointment.setDoctor(doctorRepository.findById(dto.getDoctorId()).get());
        appointment.setPet(petRepository.findById(dto.getPetId()).get());
        appointment.setOwner(ownerRepository.findById(dto.getOwnerId()).get());

        return appointment;
    }

    public static AppointmentResponseDTO fromAppointmentToAppointmentResponseDTO(Appointment appointment) {
        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setId(appointment.getId());
        response.setDateTime(appointment.getDateTime());
        response.setDurationMinutes(appointment.getDurationMinutes());
        response.setStatus(appointment.getStatus());

        if (appointment.getDoctor() != null) {
            response.setDoctorId(appointment.getDoctor().getId());
        }

        if (appointment.getPet() != null) {
            response.setPetId(appointment.getPet().getId());
        }

        if (appointment.getOwner() != null) {
            response.setOwnerId(appointment.getOwner().getId());
        }
        response.setDurationMinutes(
                appointment.getDurationMinutes() != null ?
                        appointment.getDurationMinutes() : 30
        );

        return response;
    }

    public DoctorSchedule fromDoctorScheduleRequestDTOToDoctorSchedule(DoctorScheduleRequestDTO dto) {
        DoctorSchedule schedule = new DoctorSchedule();

        // Устанавливаем основные поля
        schedule.setId(dto.getId());
        schedule.setDate(dto.getDate());
        schedule.setDayOfWeek(dto.getDayOfWeek().toUpperCase());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setWorking(dto.isWorking());

        // Находим врача по ID через репозиторий (аналогично поиску питомца в VetCard)
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Врач с ID " + dto.getDoctorId() + " не найден"));
        schedule.setDoctor(doctor);

        return schedule;
    }
}

