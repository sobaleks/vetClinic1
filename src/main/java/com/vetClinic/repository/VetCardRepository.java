package com.vetClinic.repository;

import com.vetClinic.domain.VetCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetCardRepository extends JpaRepository<VetCard, Integer> {
}
