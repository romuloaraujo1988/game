package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.Pasture;

@Repository
public interface PastureRepository extends JpaRepository<Pasture, Long> {

    @Query("SELECT SUM(p.capacity) FROM Pasture p")
    Integer sumTotalCapacity();
}
