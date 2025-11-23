package com.sistema.sicaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pastures")
@Data
@NoArgsConstructor
public class Pasture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private Double areaInHectares;

    private Integer capacity; // Max number of animals
}
