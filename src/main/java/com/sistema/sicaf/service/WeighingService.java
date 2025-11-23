package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Weighing;
import com.sistema.sicaf.repository.WeighingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeighingService {

    @Autowired
    private WeighingRepository weighingRepository;

    public List<Weighing> findByAnimalId(Long animalId) {
        return weighingRepository.findByAnimalId(animalId);
    }

    public Optional<Weighing> findById(Long id) {
        return weighingRepository.findById(id);
    }

    public Weighing save(Weighing weighing) {
        return weighingRepository.save(weighing);
    }

    public void deleteById(Long id) {
        weighingRepository.deleteById(id);
    }
}
