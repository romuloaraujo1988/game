package com.sistema.sicaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sistema.sicaf.model.Expense;
import com.sistema.sicaf.model.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.date BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalAmountByDateBetween(LocalDate startDate, LocalDate endDate);

    List<Expense> findByStatusOrderByDueDateAsc(PaymentStatus status);
}
