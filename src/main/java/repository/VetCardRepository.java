package repository;

import domain.VetCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VetCardRepository extends JpaRepository<VetCard, Integer> {
}
