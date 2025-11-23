package com.sistema.sicaf.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "deaths")
@Data
@NoArgsConstructor
public class Death {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "animal_id", nullable = false, unique = true)
    private Animal animal;

    private String cause; // Causa da morte

    private String observations;
}
