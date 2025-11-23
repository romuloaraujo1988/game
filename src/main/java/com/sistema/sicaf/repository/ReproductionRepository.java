package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.Reproduction;

@Repository
public interface ReproductionRepository extends JpaRepository<Reproduction, Long> {
}
