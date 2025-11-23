package com.sistema.sicaf.dto;

import com.sistema.sicaf.model.*;
import lombok.Data;
import java.util.List;

@Data
public class AnimalDossierDTO {
    private Animal animal;
    private List<Weighing> weighings;
    private List<Vaccination> vaccinations;
    private List<Reproduction> reproductions;
    private List<PastureMovement> movements;
    private List<FeedDistribution> feedDistributions; // Optional: inferred by pasture
}
