package com.sistema.sicaf.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "pasture_movements")
@Data
@NoArgsConstructor
public class PastureMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne
    @JoinColumn(name = "origin_pasture_id")
    private Pasture originPasture;

    @ManyToOne
    @JoinColumn(name = "destination_pasture_id", nullable = false)
    private Pasture destinationPasture;

    private String reason;
}
