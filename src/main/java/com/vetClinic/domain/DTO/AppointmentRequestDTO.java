package com.vetClinic.domain.DTO;

import lombok.Data;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {
    private int id;
    private int doctorId;       // ID ветеринара
    private int petId;         // ID питомца
    private int ownerId;       // ID владельца

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private int durationMinutes = 30; // Примитивный тип int
    private String status = "SCHEDULED";
}
