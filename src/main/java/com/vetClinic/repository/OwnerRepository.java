package com.vetClinic.repository;

import com.vetClinic.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    Optional<Owner> findOwnerByLogin(String login);
    Optional<Owner> findByEmail(String email);
    @Query(nativeQuery = true, value = "SELECT owner_id, owner.name, surname, email, tel_number,login, password, role FROM owner INNER JOIN pet on owner_id = id_own WHERE status='ожидание консультации' ")
    ArrayList<Owner> findPetsNeedConsultation();

}

