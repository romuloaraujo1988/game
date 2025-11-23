package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.FeedDistribution;

@Repository
public interface FeedDistributionRepository extends JpaRepository<FeedDistribution, Long> {
}
