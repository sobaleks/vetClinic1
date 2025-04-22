package com.vetClinic.authorization;

import com.vetClinic.domain.Doctor;
import com.vetClinic.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAuthorization extends JpaRepository<Owner, Integer> {

    @Query(nativeQuery = true, value = "SELECT owner_id, name, surname, email, tel_number,login, password, role FROM owner WHERE login=:login UNION SELECT doctor_id, doc_name, doc_surname, specialization,tel_number,doc_login, doc_pas, role FROM doctor WHERE doc_login=:login ")
    Optional<Owner> findUserOrAdminByLogin(String login);
}
