package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.AnimalStatus;
import com.sistema.sicaf.model.Death;
import com.sistema.sicaf.repository.AnimalRepository;
import com.sistema.sicaf.repository.DeathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeathService {

    @Autowired
    private DeathRepository deathRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public List<Death> findAll() {
        return deathRepository.findAll();
    }

    @Transactional
    public Death save(Death death) {
        // Save death record
        Death savedDeath = deathRepository.save(death);

        // Update Animal status to DECEASED
        Animal animal = death.getAnimal();
        // Fetch fresh to be sure
        animal = animalRepository.findById(animal.getId()).orElseThrow();

        animal.setStatus(AnimalStatus.DECEASED);
        animalRepository.save(animal);

        return savedDeath;
    }
}
