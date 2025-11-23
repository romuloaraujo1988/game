package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Vaccine;
import com.sistema.sicaf.repository.VaccineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VaccineService {

    @Autowired
    private VaccineRepository vaccineRepository;

    public List<Vaccine> findAll() {
        return vaccineRepository.findAll();
    }

    public Optional<Vaccine> findById(Long id) {
        return vaccineRepository.findById(id);
    }

    public Vaccine save(Vaccine vaccine) {
        return vaccineRepository.save(vaccine);
    }

    public void deleteById(Long id) {
        vaccineRepository.deleteById(id);
    }
}
