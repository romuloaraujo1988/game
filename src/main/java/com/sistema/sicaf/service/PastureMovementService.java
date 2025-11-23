package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.PastureMovement;
import com.sistema.sicaf.repository.AnimalRepository;
import com.sistema.sicaf.repository.PastureMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PastureMovementService {

    @Autowired
    private PastureMovementRepository movementRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public List<PastureMovement> findByAnimalId(Long animalId) {
        return movementRepository.findByAnimalIdOrderByDateDesc(animalId);
    }

    @Transactional
    public PastureMovement save(PastureMovement movement) {
        // Save movement record
        PastureMovement savedMovement = movementRepository.save(movement);

        // Update Animal's current pasture
        Animal animal = movement.getAnimal();
        // Ensure we have the latest state if needed, but object reference is usually enough if managed.
        // Better to fetch or ensure it's attached, but for now we trust the controller passed a managed or valid object.
        // Ideally we fetch it again to be safe.
        animal = animalRepository.findById(animal.getId()).orElseThrow();

        animal.setPasture(movement.getDestinationPasture());
        animalRepository.save(animal);

        return savedMovement;
    }
}
