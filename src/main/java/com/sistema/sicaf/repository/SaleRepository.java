package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.Sale;
import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT SUM(s.totalAmount) FROM Sale s WHERE s.date BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalAmountByDateBetween(LocalDate startDate, LocalDate endDate);
}
