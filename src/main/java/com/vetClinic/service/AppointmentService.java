package com.vetClinic.service;
import com.vetClinic.authorization.UserAccess;
import com.vetClinic.domain.DTO.AppointmentRequestDTO;
import com.vetClinic.domain.Appointment;
import com.vetClinic.exeptions.NoPetAppointmentsException;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.AppointmentRepository;
import com.vetClinic.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserAccess userAccess;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserAccess userAccess) {
        this.appointmentRepository = appointmentRepository;
        this.userAccess = userAccess;
    }

    // Получение записи по ID
    public Appointment getAppointmentById(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Appointment with id " + id + " not found"));

        userAccess.allUserAuthorization(appointment.getOwner().getId());
        return appointment;
    }

    // Получение всех записей (только для админа)
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        if (appointments.isEmpty()) {
            throw new ObjectNotFoundException("No appointments found");
        }
        userAccess.adminAuthorization();
        return appointments;
    }

    // Создание новой записи
    public Appointment createAppointment(AppointmentRequestDTO dto) {
        LocalDateTime end = dto.getDateTime().plusMinutes(dto.getDurationMinutes());

        if (appointmentRepository.existsConflictingAppointments(
                dto.getDoctorId(),
                dto.getDateTime(),
                end,
                0)) {  // 0 как excludeId для новой записи
            throw new IllegalStateException("Врач уже занят в это время");
        }

        Appointment appointment = DtoMapper.fromAppointmentRequestDtoToAppointment(dto);
        userAccess.adminOrDoctorAuthorization();
        return appointmentRepository.save(appointment);
    }

    public Appointment updateAppointment(AppointmentRequestDTO dto) {
        LocalDateTime end = dto.getDateTime().plusMinutes(dto.getDurationMinutes());

        if (appointmentRepository.existsConflictingAppointments(
                dto.getDoctorId(),
                dto.getDateTime(),
                end,
                dto.getId())) {
            throw new IllegalStateException("Новое время конфликтует с существующей записью");
        }

        Appointment appointment = DtoMapper.fromAppointmentRequestDtoToAppointment(dto);
        userAccess.adminOrDoctorAuthorization();
        return appointmentRepository.save(appointment);
    }

    // Удаление записи
    public void deleteAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Appointment with id " + id + " not found"));

        userAccess.adminOrDoctorAuthorization();
        appointmentRepository.delete(appointment);
    }

    // Дополнительные методы из репозитория
    public ResponseEntity<?> getAppointmentsByDoctorId(int doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorIdOrderByDateTimeDesc(doctorId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("У данного доктора нет записей");
        }

        // Обработка null-значений
        appointments.forEach(app -> {
            if (app.getDurationMinutes() == null) {
                app.setDurationMinutes(30);
            }
            if (app.getStatus() == null) {
                app.setStatus("SCHEDULED");
            }
        });

        return ResponseEntity.ok(appointments);
    }

    public ResponseEntity<?> getAppointmentsByOwnerId(int ownerId) {
        List<Appointment> appointments = appointmentRepository.findByOwnerIdOrderByDateTimeDesc(ownerId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("У данного пользователя записей нет");
        }

        // Обработка null-значений
        appointments.forEach(app -> {
            if (app.getDurationMinutes() == null) {
                app.setDurationMinutes(30);
            }
        });

        return ResponseEntity.ok(appointments);
    }

    public ResponseEntity<?> getAppointmentsByPetId(int petId) {
        List<Appointment> appointments = appointmentRepository.findByPetIdOrderByDateTimeDesc(petId);

        if (appointments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("У данного питомца нет записей");
        }

        // Обработка null-значений
        appointments.forEach(app -> {
            if (app.getDurationMinutes() == null) {
                app.setDurationMinutes(30);
            }
            if (app.getStatus() == null) {
                app.setStatus("SCHEDULED");
            }
        });

        return ResponseEntity.ok(appointments);
    }
}