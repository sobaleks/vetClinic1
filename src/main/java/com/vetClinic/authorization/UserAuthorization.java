package com.vetClinic.authorization;

import com.vetClinic.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserAuthorization extends JpaRepository<Owner, Integer> {

    @Query(nativeQuery = true, value = "SELECT owner_id, name, surname, email, tel_number,login, password, role, own_image_base64 FROM owner WHERE login=:login UNION SELECT doctor_id, full_name, email, specialisation,tel_number,doc_login, doc_pas, role, image_base64 FROM doctor WHERE doc_login=:login ")
    Optional<Owner> findUserOrAdminByLogin(String login);
}
