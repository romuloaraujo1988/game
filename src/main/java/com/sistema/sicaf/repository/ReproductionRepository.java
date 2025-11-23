package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.Reproduction;
import java.util.List;

@Repository
public interface ReproductionRepository extends JpaRepository<Reproduction, Long> {
    List<Reproduction> findByDamId(Long damId);
}
