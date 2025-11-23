package com.sistema.sicaf.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date; // Data de competência/registro

    private String description;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    // Financial Control
    private LocalDate dueDate; // Vencimento

    private LocalDate paymentDate; // Data do Pagamento Realizado

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDING;
}
