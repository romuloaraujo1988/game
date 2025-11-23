package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.Weighing;
import java.util.List;

@Repository
public interface WeighingRepository extends JpaRepository<Weighing, Long> {
    List<Weighing> findByAnimalId(Long animalId);
}
