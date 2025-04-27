package com.vetClinic.repository;

import com.vetClinic.domain.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findDoctorByLogin(String login);

    // Поиск по специальности (частичное совпадение, игнор регистра)
    @Query("SELECT d FROM Doctor d WHERE LOWER(d.specialisation) LIKE LOWER(CONCAT('%', :specialisation, '%'))")
    List<Doctor> findBySpecialisation(@Param("specialisation") String specialisation);

    @Query(value = """
    SELECT 
        d.specialisation AS specialisation,
        json_agg(
            json_build_object(
                'id', d.doctor_id,
                'fullName', d.full_name,
                'hashTags', CASE\s
                        
                                    WHEN d.hash_tag IS NOT NULL AND d.hash_tag != ''\s
                                    THEN string_to_array(d.hash_tag, ',')\s
                                    ELSE array['Питание','Лечение','Забота']
                                END,
                'description', COALESCE(d.description, 'Нет описания'),
                'schedule', (
                                     SELECT array_agg(ds.date ORDER BY ds.date)
                                     FROM doctor_schedule ds
                                     WHERE ds.doctor_id = d.doctor_id
                                     AND ds.is_working = true
                                     AND ds.date >= CURRENT_DATE
                                     LIMIT 5
                                 )
                             )
        ) AS doctors
    FROM doctor d
    GROUP BY d.specialisation
    ORDER BY d.specialisation
    """, nativeQuery = true)
    List<Map<String, Object>> findDoctorsWithSchedule();
}
