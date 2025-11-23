package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.Death;

@Repository
public interface DeathRepository extends JpaRepository<Death, Long> {
}
