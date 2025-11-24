package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.Sale;
import com.sistema.sicaf.model.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT SUM(s.totalAmount) FROM Sale s WHERE s.date BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalAmountByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Sale> findByStatusOrderByDueDateAsc(PaymentStatus status);

    List<Sale> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // For Alerts
    List<Sale> findByStatusAndDueDateLessThanEqualOrderByDueDateAsc(PaymentStatus status, LocalDate date);
}
