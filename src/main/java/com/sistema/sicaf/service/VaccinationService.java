package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Vaccination;
import com.sistema.sicaf.repository.VaccinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccinationService {

    @Autowired
    private VaccinationRepository vaccinationRepository;

    public List<Vaccination> findByAnimalId(Long animalId) {
        return vaccinationRepository.findByAnimalId(animalId);
    }

    public Vaccination save(Vaccination vaccination) {
        return vaccinationRepository.save(vaccination);
    }

    public void deleteById(Long id) {
        vaccinationRepository.deleteById(id);
    }

    public Optional<Vaccination> findById(Long id) {
        return vaccinationRepository.findById(id);
    }
}
