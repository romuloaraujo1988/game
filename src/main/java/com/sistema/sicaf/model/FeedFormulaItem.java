package com.sistema.sicaf.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "feed_formula_items")
@Data
@NoArgsConstructor
public class FeedFormulaItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "formula_id", nullable = false)
    private FeedFormula formula;

    @ManyToOne
    @JoinColumn(name = "input_id", nullable = false)
    private FeedInput input;

    private Double percentage; // Porcentagem na receita (Ex: 60% Milho)
}
