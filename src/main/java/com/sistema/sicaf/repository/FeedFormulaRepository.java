package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.FeedFormula;

@Repository
public interface FeedFormulaRepository extends JpaRepository<FeedFormula, Long> {
}
