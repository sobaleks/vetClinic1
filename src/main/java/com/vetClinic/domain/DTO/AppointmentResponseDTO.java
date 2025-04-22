package com.vetClinic.domain.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class AppointmentResponseDTO {
    private int id;
    private int doctorId;
    private int petId;
    private int ownerId;
    private LocalDateTime dateTime;
    private int durationMinutes;
    private String status;

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    // ... остальные геттеры/сеттеры
}
