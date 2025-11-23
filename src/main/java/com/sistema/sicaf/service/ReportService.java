package com.sistema.sicaf.service;

import com.sistema.sicaf.dto.AnimalDossierDTO;
import com.sistema.sicaf.model.Animal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    @Autowired
    private AnimalService animalService;
    @Autowired
    private WeighingService weighingService;
    @Autowired
    private VaccinationService vaccinationService;
    @Autowired
    private ReproductionService reproductionService;
    @Autowired
    private PastureMovementService movementService;

    @Transactional(readOnly = true)
    public AnimalDossierDTO generateAnimalDossier(Long animalId) {
        Animal animal = animalService.findById(animalId).orElseThrow(() -> new RuntimeException("Animal não encontrado"));

        AnimalDossierDTO dossier = new AnimalDossierDTO();
        dossier.setAnimal(animal);
        dossier.setWeighings(weighingService.findByAnimalId(animalId));
        dossier.setVaccinations(vaccinationService.findByAnimalId(animalId));
        dossier.setMovements(movementService.findByAnimalId(animalId));

        // Reproduction only if female
        if (animal.getGender() == com.sistema.sicaf.model.Gender.FEMALE) {
             dossier.setReproductions(reproductionService.findByDamId(animalId));
        }

        return dossier;
    }
}
