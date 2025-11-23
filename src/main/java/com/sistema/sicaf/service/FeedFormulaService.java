package com.sistema.sicaf.service;

import com.sistema.sicaf.model.FeedFormula;
import com.sistema.sicaf.model.FeedFormulaItem;
import com.sistema.sicaf.repository.FeedFormulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedFormulaService {

    @Autowired
    private FeedFormulaRepository feedFormulaRepository;

    public List<FeedFormula> findAll() {
        return feedFormulaRepository.findAll();
    }

    public FeedFormula save(FeedFormula formula) {
        // Link items to formula
        if (formula.getItems() != null) {
            for (FeedFormulaItem item : formula.getItems()) {
                item.setFormula(formula);
            }
        }
        return feedFormulaRepository.save(formula);
    }

    public Optional<FeedFormula> findById(Long id) {
        return feedFormulaRepository.findById(id);
    }

    public void deleteById(Long id) {
        feedFormulaRepository.deleteById(id);
    }
}
