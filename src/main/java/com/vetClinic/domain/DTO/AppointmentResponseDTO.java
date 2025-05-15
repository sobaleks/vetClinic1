package com.vetClinic.domain.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AppointmentResponseDTO {
    private int id;

    private int ownerId;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private String status;
    private DoctorResponseDTO doctor;
    private PetResponseDTO pet;  // Создадим DTO для ответа по питомцу


}
