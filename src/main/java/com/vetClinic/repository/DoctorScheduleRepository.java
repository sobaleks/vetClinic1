package com.vetClinic.repository;

import com.vetClinic.domain.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {

    // Найти все расписания для указанного врача
    List<DoctorSchedule> findByDoctorId(int doctorId);

    // Найти все активные (is_working = true) расписания
    List<DoctorSchedule> findByIsWorkingTrue();

    List<DoctorSchedule> findByDate(LocalDate date);
    // Найти расписания по дню недели (например, "ПОНЕДЕЛЬНИК")
    List<DoctorSchedule> findByDayOfWeek(String dayOfWeek);

    @Query("SELECT ds FROM DoctorSchedule ds WHERE ds.doctor.id = :doctorId AND ds.isWorking = true")
    List<DoctorSchedule> findActiveSchedulesByDoctorId(@Param("doctorId") int doctorId);

    @Query("SELECT ds FROM DoctorSchedule ds WHERE ds.dayOfWeek = :day AND ds.startTime <= :time AND ds.endTime >= :time")
    List<DoctorSchedule> findSchedulesByDayAndTime(@Param("day") String day, @Param("time") Time time);

    @Query("""
    SELECT COUNT(ds) > 0
    FROM DoctorSchedule ds
    WHERE ds.doctor.id = :doctorId
      AND (
           (ds.date IS NULL AND ds.dayOfWeek = :dayOfWeek) OR
           (ds.date = :date)
      )
      AND ds.isWorking = true
      AND :startTime >= ds.startTime AND :endTime <= ds.endTime
""")
    boolean isDoctorWorking(
            @Param("doctorId") int doctorId,
            @Param("dayOfWeek") String dayOfWeek,
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime
    );

}
