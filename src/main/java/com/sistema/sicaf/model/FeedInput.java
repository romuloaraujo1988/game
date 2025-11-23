package com.sistema.sicaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "feed_inputs")
@Data
@NoArgsConstructor
public class FeedInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name; // Ex: Milho Moído, Farelo de Soja

    private Double stockQuantityKg; // Estoque atual em Kg

    private BigDecimal unitCostPerKg; // Custo médio por Kg
}
