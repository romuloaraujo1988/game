package com.sistema.sicaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "animals")
@Data
@NoArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String earTag; // Brinco

    private String breed; // Raça

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "pasture_id")
    private Pasture pasture;

    // Status: ACTIVE, SOLD, DECEASED
    @Enumerated(EnumType.STRING)
    private AnimalStatus status = AnimalStatus.ACTIVE;
}
