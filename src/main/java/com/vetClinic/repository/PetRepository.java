package com.vetClinic.repository;

import com.vetClinic.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {


    @Modifying
    @Query(nativeQuery = true, value = "UPDATE pet SET status ='ожидание консультации' where name=:name")
    void recodingConsultation(String name);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE pet SET status =:changeStatus where pet_id=:id  ")
    void changeStatus(int id, String changeStatus);

    @Modifying
    @Query(nativeQuery = true, value = "SELECT  pet_id,  name, breed, age, status, id_own FROM pet  WHERE id_own=:idOwn")
    ArrayList<Pet> getPetsByIdOwn(int idOwn);

    Optional<Pet> findPetByName(String name);
}
