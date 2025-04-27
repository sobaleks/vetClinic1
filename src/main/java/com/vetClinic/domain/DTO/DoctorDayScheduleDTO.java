package com.vetClinic.domain.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
public class DoctorDayScheduleDTO {
    private LocalDate date;
    private boolean isWorkingDay;
    private LocalTime from;
    private LocalTime to;
    private List<LocalTime> notAvailable;
    private List<LocalTime> booked;
}
