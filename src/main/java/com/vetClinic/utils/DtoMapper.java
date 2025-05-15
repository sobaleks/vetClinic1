package com.vetClinic.utils;

import com.vetClinic.domain.Appointment;
import com.vetClinic.domain.DTO.AppointmentRequestDTO;
import com.vetClinic.domain.DTO.AppointmentResponseDTO;
import com.vetClinic.domain.DTO.DoctorDayScheduleDTO;
import com.vetClinic.domain.DTO.DoctorResponseDTO;
import com.vetClinic.domain.DTO.DoctorScheduleRequestDTO;
import com.vetClinic.domain.DTO.OwnerResponseDTO;
import com.vetClinic.domain.DTO.PetRequestDTO;
import com.vetClinic.domain.DTO.PetResponseDTO;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        vetCard.setVaccination(vetCardRequestDTO.getVaccination());
        vetCard.setPet(petRepository.findById(vetCardRequestDTO.getIdPet()).get());
        return vetCard;
    }

    public static Pet fromPetRequestDtoToPet(PetRequestDTO petRequestDTO) {
        Pet pet = new Pet();
        pet.setId(petRequestDTO.getId());
        pet.setName(petRequestDTO.getName());
        pet.setType(petRequestDTO.getType());
        pet.setBreed(petRequestDTO.getBreed());
        pet.setGender(petRequestDTO.getGender());
        pet.setWeight(petRequestDTO.getWeight());
        pet.setPassport(petRequestDTO.getPassport());
        pet.setDateOfBirth(petRequestDTO.getDateOfBirth());
        pet.setImageBase64(petRequestDTO.getImageBase64());
        if (petRequestDTO.getStatus() != null && !petRequestDTO.getStatus().isEmpty()) {
            pet.setStatus(petRequestDTO.getStatus());
        }
        pet.setOwner(ownerRepository.findById(petRequestDTO.getIdOwn()).get());
        return pet;
    }

    public static OwnerResponseDTO fromOwnerToOwnerResponseDTO(Owner owner) {
        OwnerResponseDTO ownerResponseDTO = new OwnerResponseDTO();
        ownerResponseDTO.setId(owner.getId());
        ownerResponseDTO.setName(owner.getName());
        ownerResponseDTO.setSurname(owner.getSurname());
        ownerResponseDTO.setEmail(owner.getEmail());
        ownerResponseDTO.setTelephoneNumber(owner.getTelephoneNumber());
        ownerResponseDTO.setImageBase64(owner.getImageBase64());
        ownerResponseDTO.setPetsList(owner.getPetsList());
        return ownerResponseDTO;
    }

    public static DoctorResponseDTO fromDoctorToDoctorResponseDTO(Doctor doctor) {
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setName(doctor.getName());
        doctorResponseDTO.setEmail(doctor.getEmail());
        doctorResponseDTO.setTelephoneNumber(doctor.getTelephoneNumber());
        doctorResponseDTO.setSpecialisation(doctor.getSpecialisation());
        doctorResponseDTO.setDescription(doctor.getDescription());
        doctorResponseDTO.setHashTag(doctor.getHashTag());
        doctorResponseDTO.setImageBase64(doctor.getImageBase64());
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
            Doctor doctor = appointment.getDoctor();
            DoctorResponseDTO doctorDTO = new DoctorResponseDTO();
            doctorDTO.setName(doctor.getName());
            doctorDTO.setEmail(doctor.getEmail());
            doctorDTO.setSpecialisation(doctor.getSpecialisation());
            doctorDTO.setTelephoneNumber(doctor.getTelephoneNumber());
            doctorDTO.setDescription(doctor.getDescription());
            doctorDTO.setHashTag(doctor.getHashTag());
            doctorDTO.setImageBase64(doctor.getImageBase64());
            response.setDoctor(doctorDTO);
        }

        if (appointment.getPet() != null) {
            Pet pet = appointment.getPet();
            PetResponseDTO petDTO = new PetResponseDTO();
            petDTO.setId(pet.getId());
            petDTO.setName(pet.getName());
            petDTO.setType(pet.getType());
            petDTO.setBreed(pet.getBreed());
            petDTO.setDateOfBirth(pet.getDateOfBirth());
            petDTO.setGender(pet.getGender());
            petDTO.setWeight(pet.getWeight());
            petDTO.setPassport(pet.getPassport());
            petDTO.setImageBase64(pet.getImageBase64());
            petDTO.setStatus(pet.getStatus());
            petDTO.setIdOwn(pet.getOwner() != null ? pet.getOwner().getId() : 0);
            response.setPet(petDTO);
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

        // Находим врача по ID через репозиторий
        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Врач с ID " + dto.getDoctorId() + " не найден"));
        schedule.setDoctor(doctor);

        return schedule;
    }
    public DoctorDayScheduleDTO toDoctorDayScheduleDTO(LocalDate date, DoctorSchedule daySchedule, List<Appointment> appointments) {
        DoctorDayScheduleDTO dto = new DoctorDayScheduleDTO();
        dto.setDate(date);

        if (daySchedule != null && daySchedule.isWorking()) {
            dto.setWorkingDay(true);
            dto.setFrom(daySchedule.getStartTime().toLocalTime());
            dto.setTo(daySchedule.getEndTime().toLocalTime());
        } else {
            dto.setWorkingDay(false);
            dto.setFrom(LocalTime.of(0, 0));
            dto.setTo(LocalTime.of(23, 59));
        }

        List<LocalTime> booked = appointments.stream()
                .map(app -> app.getDateTime().toLocalTime())
                .collect(Collectors.toList());

        List<LocalTime> notAvailable = new ArrayList<>(); // пока без логики перерывов

        dto.setBooked(booked);
        dto.setNotAvailable(notAvailable);

        return dto;
    }

}

