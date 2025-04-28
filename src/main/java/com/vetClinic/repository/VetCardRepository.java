package com.vetClinic.repository;

import com.vetClinic.domain.VetCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VetCardRepository extends JpaRepository<VetCard, Integer> {
    @Query("SELECT v.vaccination FROM VetCard v WHERE v.pet.id = :petId AND v.vaccination IS NOT NULL")
    List<String> findVaccinationsByPetId(@Param("petId") int petId);

}
