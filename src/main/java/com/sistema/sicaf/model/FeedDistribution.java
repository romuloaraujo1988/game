package com.sistema.sicaf.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "feed_distributions")
@Data
@NoArgsConstructor
public class FeedDistribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "pasture_id", nullable = false)
    private Pasture pasture;

    @ManyToOne
    @JoinColumn(name = "formula_id", nullable = false)
    private FeedFormula formula;

    private Double quantityKg; // Quantidade fornecida no cocho

    private String observations;
}
