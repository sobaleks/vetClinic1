package com.vetClinic.repository;

import com.vetClinic.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query("SELECT a FROM Appointment a LEFT JOIN FETCH a.pet WHERE a.doctor.id = :doctorId ORDER BY a.dateTime DESC")
    List<Appointment> findByDoctorIdOrderByDateTimeDesc(Integer doctorId);
    List<Appointment> findByPetIdOrderByDateTimeDesc(Integer petId);
    List<Appointment> findByOwnerIdOrderByDateTimeDesc(Integer ownerId);

    @Query(value = """
    SELECT EXISTS(
        SELECT 1 FROM appointment a 
        WHERE 
            a.doctor_id = :doctorId AND 
            a.date_time < :end AND 
            (a.date_time + (a.duration_minutes * INTERVAL '1 minute')) > :start AND 
            a.id <> :excludeId
    )""", nativeQuery = true)
    boolean existsConflictingAppointments(
            @Param("doctorId") Integer doctorId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("excludeId") Integer excludeId);

    boolean existsByDoctorIdAndDateTimeBetween(Integer doctorId, LocalDateTime start, LocalDateTime end);
    @Query("""
        SELECT a FROM Appointment a
        WHERE a.doctor.id = :doctorId
    """)
    List<Appointment> findAppointmentsByDoctorId(@Param("doctorId") Integer doctorId);
}
