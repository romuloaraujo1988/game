package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Reproduction;
import com.sistema.sicaf.repository.ReproductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReproductionService {

    @Autowired
    private ReproductionRepository reproductionRepository;

    public List<Reproduction> findByDamId(Long damId) {
        return reproductionRepository.findByDamId(damId);
    }

    public Reproduction save(Reproduction reproduction) {
        return reproductionRepository.save(reproduction);
    }

    public void deleteById(Long id) {
        reproductionRepository.deleteById(id);
    }

    public Optional<Reproduction> findById(Long id) {
        return reproductionRepository.findById(id);
    }
}
