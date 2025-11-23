package com.sistema.sicaf.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "feed_productions")
@Data
@NoArgsConstructor
public class FeedProduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "formula_id", nullable = false)
    private FeedFormula formula;

    private Double totalQuantityKg; // Total produzido (Ex: 1000kg)

    private String observations;
}
