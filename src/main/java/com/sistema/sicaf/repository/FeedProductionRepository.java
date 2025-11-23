package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.FeedProduction;

@Repository
public interface FeedProductionRepository extends JpaRepository<FeedProduction, Long> {
}
