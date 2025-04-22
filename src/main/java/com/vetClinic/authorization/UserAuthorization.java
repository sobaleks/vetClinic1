package com.vetClinic.authorization;

import com.vetClinic.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAuthorization extends JpaRepository<Owner, Integer> {

    @Query(nativeQuery = true, value = "SELECT owner_id, name, surname, email, tel_number,login, password, role FROM owner WHERE login=:login UNION SELECT doctor_id, full_name, email, specialization,tel_number,staff_login, staff_pas, role FROM doctor WHERE staff_login=:login ")
    Optional<Owner> findUserOrAdminByLogin(String login);
}
