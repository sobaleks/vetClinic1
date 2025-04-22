package com.vetClinic.service;

import com.vetClinic.authorization.UserAccess;
import com.vetClinic.domain.DTO.DoctorScheduleRequestDTO;
import com.vetClinic.domain.DoctorSchedule;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.DoctorScheduleRepository;
import com.vetClinic.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final UserAccess userAccess;
    private final DtoMapper dtoMapper;

    @Autowired
    public DoctorScheduleService(DoctorScheduleRepository doctorScheduleRepository,
                                 UserAccess userAccess,
                                 DtoMapper dtoMapper) {
        this.doctorScheduleRepository = doctorScheduleRepository;
        this.userAccess = userAccess;
        this.dtoMapper = dtoMapper;
    }

    // Получить расписание по ID
    public DoctorSchedule getScheduleById(int id) {
        DoctorSchedule schedule = doctorScheduleRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Расписание с ID " + id + " не найдено"));
        userAccess.adminOrDoctorAuthorization();
        return schedule;
    }

    // Получить все расписания
    public List<DoctorSchedule> getAllSchedules() {
        List<DoctorSchedule> schedules = (ArrayList<DoctorSchedule>) doctorScheduleRepository.findAll();
        if (schedules.isEmpty()) {
            throw new ObjectNotFoundException("Расписания не найдены");
        }
        userAccess.adminAuthorization();
        return schedules;
    }

    // Создать новое расписание
    public DoctorSchedule createSchedule(DoctorScheduleRequestDTO scheduleDTO) {
        DoctorSchedule schedule = dtoMapper.fromDoctorScheduleRequestDTOToDoctorSchedule(scheduleDTO);
        userAccess.adminOrDoctorAuthorization();
        return doctorScheduleRepository.save(schedule);
    }

    // Обновить расписание
    public DoctorSchedule updateSchedule(DoctorScheduleRequestDTO scheduleDTO) {
        doctorScheduleRepository.findById(scheduleDTO.getId()).orElseThrow(
                () -> new ObjectNotFoundException("Расписание с ID " + scheduleDTO.getId() + " не найдено")
        );
        userAccess.adminOrDoctorAuthorization();
        DoctorSchedule schedule = dtoMapper.fromDoctorScheduleRequestDTOToDoctorSchedule(scheduleDTO);
        return doctorScheduleRepository.save(schedule);
    }

    // Удалить расписание
    public void deleteSchedule(int id) {
        doctorScheduleRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Расписание с ID " + id + " не найдено")
        );
        userAccess.adminOrDoctorAuthorization();
        doctorScheduleRepository.deleteById(id);
    }

    // Найти расписания по дню недели (например, "ПОНЕДЕЛЬНИК")
    public List<DoctorSchedule> getSchedulesByDayOfWeek(String dayOfWeek) {
        List<DoctorSchedule> schedules = doctorScheduleRepository.findByDayOfWeek(dayOfWeek.toUpperCase());
        if (schedules.isEmpty()) {
            throw new ObjectNotFoundException("Расписания для дня '" + dayOfWeek + "' не найдены");
        }
        userAccess.adminOrDoctorAuthorization();
        return schedules;
    }

    // Найти активные расписания врача (is_working = true)
    public List<DoctorSchedule> getActiveSchedulesByDoctorId(int doctorId) {
        List<DoctorSchedule> schedules = doctorScheduleRepository.findActiveSchedulesByDoctorId(doctorId);
        if (schedules.isEmpty()) {
            throw new ObjectNotFoundException("Активные расписания для врача с ID " + doctorId + " не найдены");
        }
        userAccess.allUserAuthorization(doctorId); // Или adminOrDoctorAuthorization() в зависимости от логики
        return schedules;
    }

    // Найти расписания по дню и времени (для проверки доступности врача)
    public List<DoctorSchedule> getSchedulesByDayAndTime(String day, Time time) {
        List<DoctorSchedule> schedules = doctorScheduleRepository.findSchedulesByDayAndTime(day.toUpperCase(), time);
        if (schedules.isEmpty()) {
            throw new ObjectNotFoundException("Врачи не доступны в " + time + " в день '" + day + "'");
        }
        userAccess.adminOrDoctorAuthorization(); // Или другая проверка прав
        return schedules;
    }

    // Проверить, доступен ли врач в указанное время (доп. удобный метод)
    public boolean isDoctorAvailable(int doctorId, String day, Time time) {
        List<DoctorSchedule> schedules = doctorScheduleRepository.findSchedulesByDayAndTime(day.toUpperCase(), time);
        return schedules.stream()
                .anyMatch(schedule ->
                        schedule.getDoctor().getId() == doctorId &&
                                schedule.isWorking()
                );
    }

    public List<DoctorSchedule> getSchedulesByDate(LocalDate date) {
        List<DoctorSchedule> schedules = doctorScheduleRepository.findByDate(date);
        if (schedules.isEmpty()) {
            throw new ObjectNotFoundException("Расписания на дату " + date + " не найдены");
        }
        userAccess.adminOrDoctorAuthorization();
        return schedules;
    }
}