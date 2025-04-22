package com.vetClinic.domain.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleRequestDTO {

    private int id;

    @NotNull(message = "ID врача обязательно")
    private int doctorId;
    private LocalDate date;
    @NotBlank(message = "День недели обязателен")
    @Pattern(regexp = "^(ПОНЕДЕЛЬНИК|ВТОРНИК|СРЕДА|ЧЕТВЕРГ|ПЯТНИЦА|СУББОТА|ВОСКРЕСЕНЬЕ)$",
            message = "День недели должен быть в формате: ПОНЕДЕЛЬНИК, ВТОРНИК и т.д.")
    private String dayOfWeek;  // Строка в верхнем регистре

    @NotNull(message = "Время начала обязательно")
    private Time startTime;

    @NotNull(message = "Время окончания обязательно")
    private Time endTime;

    private boolean isWorking = true;
}