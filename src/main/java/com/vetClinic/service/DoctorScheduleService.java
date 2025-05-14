package com.vetClinic.service;

import com.vetClinic.authorization.UserAccess;
import com.vetClinic.domain.Appointment;
import com.vetClinic.domain.DTO.DoctorDayScheduleDTO;
import com.vetClinic.domain.DTO.DoctorScheduleRequestDTO;
import com.vetClinic.domain.DoctorSchedule;
import com.vetClinic.exeptions.ObjectNotFoundException;
import com.vetClinic.repository.AppointmentRepository;
import com.vetClinic.repository.DoctorScheduleRepository;
import com.vetClinic.utils.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final UserAccess userAccess;
    private final DtoMapper dtoMapper;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DoctorScheduleService(DoctorScheduleRepository doctorScheduleRepository,
                                 UserAccess userAccess, DtoMapper dtoMapper,
                                 AppointmentRepository appointmentRepository) {
        this.doctorScheduleRepository = doctorScheduleRepository;
        this.userAccess = userAccess;
        this.dtoMapper = dtoMapper;
        this.appointmentRepository = appointmentRepository;
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
    public List<Map<String, Object>> getDoctorFullSchedule(Integer doctorId) {
        List<DoctorSchedule> workingDays = doctorScheduleRepository.findWorkingDaysByDoctorId(doctorId);
        List<Appointment> appointments = appointmentRepository.findAppointmentsByDoctorId(doctorId);

        Map<LocalDate, DoctorSchedule> scheduleMap = workingDays.stream()
                .collect(Collectors.toMap(DoctorSchedule::getDate, Function.identity()));

        Map<LocalDate, List<LocalTime>> bookedMap = new HashMap<>();
        for (Appointment app : appointments) {
            LocalDate date = app.getDateTime().toLocalDate();
            LocalTime time = app.getDateTime().toLocalTime();
            bookedMap.computeIfAbsent(date, k -> new ArrayList<>()).add(time);
        }

        List<Map<String, Object>> result = new ArrayList<>();

        LocalDate today = LocalDate.now();
        LocalDate twoWeeksLater = today.plusDays(13);

        for (LocalDate date = today; !date.isAfter(twoWeeksLater); date = date.plusDays(1)) {
            Map<String, Object> dayInfo = new HashMap<>();
            dayInfo.put("date", date.toString());

            if (scheduleMap.containsKey(date)) {
                DoctorSchedule schedule = scheduleMap.get(date);
                LocalTime from = schedule.getStartTime().toLocalTime();
                LocalTime to = schedule.getEndTime().toLocalTime();

                dayInfo.put("isWorkingDay", true);
                dayInfo.put("from", from);
                dayInfo.put("to", to);

                // Расчёт времени обеда
                List<LocalTime> notAvailable = calculateLunchBreak(from, to);

                List<LocalTime> booked = bookedMap.getOrDefault(date, new ArrayList<>());
                dayInfo.put("booked", booked);
                dayInfo.put("notAvailable", notAvailable);
            } else {
                // Врач не работает в этот день
                dayInfo.put("isWorkingDay", false);
                dayInfo.put("from", null);
                dayInfo.put("to", null);
                dayInfo.put("booked", Collections.emptyList());
                dayInfo.put("notAvailable", Collections.emptyList());
            }

            result.add(dayInfo);
        }

        return result;
    }

    // Метод для расчета обеда
    private List<LocalTime> calculateLunchBreak(LocalTime from, LocalTime to) {
        long workMinutes = Duration.between(from, to).toMinutes();

        if (workMinutes < 240) { // меньше 4 часов
            return Collections.emptyList();
        }

        // Определяем середину рабочего дня
        long halfWorkMinutes = workMinutes / 2;
        LocalTime lunchStart = from.plusMinutes(halfWorkMinutes);
        LocalTime lunchEnd = lunchStart.plusMinutes(30);

        return List.of(lunchStart, lunchEnd);
    }
}