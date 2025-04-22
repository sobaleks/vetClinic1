package com.vetClinic.controller;
import com.vetClinic.domain.DTO.AppointmentRequestDTO;
import com.vetClinic.domain.Appointment;
import com.vetClinic.domain.DTO.AppointmentResponseDTO;
import com.vetClinic.service.AppointmentService;
import com.vetClinic.utils.DtoMapper;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final DtoMapper dtoMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public AppointmentController(AppointmentService appointmentService, DtoMapper dtoMapper) {
        this.appointmentService = appointmentService;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(@PathVariable int id) {
        Appointment appointment = appointmentService.getAppointmentById(id);

        // Защита от null в durationMinutes
        if (appointment.getDurationMinutes() == null) {
            appointment.setDurationMinutes(30); // Устанавливаем дефолтное значение
        }

        AppointmentResponseDTO response = DtoMapper.fromAppointmentToAppointmentResponseDTO(appointment);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllAppointments() {
        logger.info("GET /appointments - Getting all appointments");
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointments, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(
            @RequestBody @Valid AppointmentRequestDTO appointmentRequestDTO) {
        logger.info("POST /appointments - Creating new appointment");
        Appointment appointment = appointmentService.createAppointment(appointmentRequestDTO);
        return new ResponseEntity<>(appointment, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateAppointment(
            @RequestBody @Valid AppointmentRequestDTO appointmentRequestDTO) {
        logger.info("PUT /appointments - Updating appointment");
        Appointment appointment = appointmentService.updateAppointment(appointmentRequestDTO);
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable int id) {
        logger.info("DELETE /appointments/{} - Deleting appointment", id);
        appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Дополнительные endpoints
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAppointmentsByDoctor(@PathVariable int doctorId) {
        return appointmentService.getAppointmentsByDoctorId(doctorId);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<?> getAppointmentsByOwner(@PathVariable int ownerId) {
        return ResponseEntity.ok(appointmentService.getAppointmentsByOwnerId(ownerId));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<?> getAppointmentsByPet(@PathVariable int petId) {
        return appointmentService.getAppointmentsByPetId(petId);
    }
}
