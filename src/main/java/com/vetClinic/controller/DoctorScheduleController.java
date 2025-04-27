package com.vetClinic.controller;

import com.vetClinic.domain.DTO.DoctorDayScheduleDTO;
import com.vetClinic.domain.DTO.DoctorScheduleRequestDTO;
import com.vetClinic.domain.DoctorSchedule;
import com.vetClinic.service.DoctorScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schedule")
public class DoctorScheduleController {

    private final DoctorScheduleService doctorScheduleService;

    @Autowired
    public DoctorScheduleController(DoctorScheduleService doctorScheduleService) {
        this.doctorScheduleService = doctorScheduleService;
    }

    // Получить расписание по ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorSchedule> getScheduleById(@PathVariable int id) {
        DoctorSchedule schedule = doctorScheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    // Получить все расписания (только для админов)
    @GetMapping
    public ResponseEntity<List<DoctorSchedule>> getAllSchedules() {
        List<DoctorSchedule> schedules = doctorScheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    // Создать новое расписание
    @PostMapping
    public ResponseEntity<DoctorSchedule> createSchedule(@RequestBody DoctorScheduleRequestDTO scheduleDTO) {
        DoctorSchedule createdSchedule = doctorScheduleService.createSchedule(scheduleDTO);
        return ResponseEntity.ok(createdSchedule);
    }

    // Обновить расписание
    @PutMapping
    public ResponseEntity<DoctorSchedule> updateSchedule(@RequestBody DoctorScheduleRequestDTO scheduleDTO) {
        DoctorSchedule updatedSchedule = doctorScheduleService.updateSchedule(scheduleDTO);
        return ResponseEntity.ok(updatedSchedule);
    }

    // Удалить расписание
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
        doctorScheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }

    // Дополнительные методы

    // Получить расписания по дню недели
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<DoctorSchedule>> getSchedulesByDay(@PathVariable String dayOfWeek) {
        List<DoctorSchedule> schedules = doctorScheduleService.getSchedulesByDayOfWeek(dayOfWeek);
        return ResponseEntity.ok(schedules);
    }

    // Получить активные расписания врача
    @GetMapping("/doctor/{doctorId}/active")
    public ResponseEntity<List<DoctorSchedule>> getActiveSchedulesByDoctor(@PathVariable int doctorId) {
        List<DoctorSchedule> schedules = doctorScheduleService.getActiveSchedulesByDoctorId(doctorId);
        return ResponseEntity.ok(schedules);
    }

    // Проверить доступность врача
    @GetMapping("/availability")
    public ResponseEntity<Boolean> checkAvailability(
            @RequestParam int doctorId,
            @RequestParam String day,
            @RequestParam String time) {
        boolean isAvailable = doctorScheduleService.isDoctorAvailable(
                doctorId,
                day,
                Time.valueOf(time) // Конвертируем строку в Time
        );
        return ResponseEntity.ok(isAvailable);
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<List<DoctorSchedule>> getSchedulesByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(doctorScheduleService.getSchedulesByDate(date));
    }
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Map<String, Object>>> getDoctorSchedule(@PathVariable Integer doctorId) {
        return ResponseEntity.ok(doctorScheduleService.getDoctorFullSchedule(doctorId));
    }
}