package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Pasture;
import com.sistema.sicaf.repository.PastureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PastureService {

    @Autowired
    private PastureRepository pastureRepository;

    public List<Pasture> findAll() {
        return pastureRepository.findAll();
    }

    public Optional<Pasture> findById(Long id) {
        return pastureRepository.findById(id);
    }

    public Pasture save(Pasture pasture) {
        return pastureRepository.save(pasture);
    }

    public void deleteById(Long id) {
        pastureRepository.deleteById(id);
    }
}
