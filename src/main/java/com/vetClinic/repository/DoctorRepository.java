package com.vetClinic.repository;

import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    Optional<Doctor> findDoctorByLogin(String login);
}
