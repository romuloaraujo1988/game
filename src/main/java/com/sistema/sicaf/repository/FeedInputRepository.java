package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.FeedInput;
import java.util.List;

@Repository
public interface FeedInputRepository extends JpaRepository<FeedInput, Long> {
    // Find low stock
    List<FeedInput> findByStockQuantityKgLessThan(Double quantity);
}
