package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.FeedInput;

@Repository
public interface FeedInputRepository extends JpaRepository<FeedInput, Long> {
}
