package com.vetClinic.repository;

import com.vetClinic.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PetRepository  extends JpaRepository<Pet, Integer> {

    @Query
    ArrayList<Pet> getPetsByIdOwn(int idOwn);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE pet SET status ='ожидание консультации' where pet_id=:id")
    void recodingConsultation(int id);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE pet SET status =:changeStatus where pet_id=:id  ")
    void change(int id, String changeStatus);
}
