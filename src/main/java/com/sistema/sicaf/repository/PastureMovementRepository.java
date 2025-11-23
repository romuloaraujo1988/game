package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.PastureMovement;
import java.util.List;

@Repository
public interface PastureMovementRepository extends JpaRepository<PastureMovement, Long> {
    List<PastureMovement> findByAnimalIdOrderByDateDesc(Long animalId);
}
