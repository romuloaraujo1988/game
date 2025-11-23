package com.sistema.sicaf.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "reproductions")
@Data
@NoArgsConstructor
public class Reproduction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dam_id", nullable = false) // Mãe
    private Animal dam;

    @ManyToOne
    @JoinColumn(name = "sire_id") // Pai (opcional, inseminação)
    private Animal sire;

    private LocalDate date;

    private String type; // Natural, Insemination

    private String observations;
}
