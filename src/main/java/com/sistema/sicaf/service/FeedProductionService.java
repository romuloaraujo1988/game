package com.sistema.sicaf.service;

import com.sistema.sicaf.model.FeedFormulaItem;
import com.sistema.sicaf.model.FeedInput;
import com.sistema.sicaf.model.FeedProduction;
import com.sistema.sicaf.repository.FeedInputRepository;
import com.sistema.sicaf.repository.FeedProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedProductionService {

    @Autowired
    private FeedProductionRepository feedProductionRepository;

    @Autowired
    private FeedInputRepository feedInputRepository;

    public List<FeedProduction> findAll() {
        return feedProductionRepository.findAll();
    }

    @Transactional
    public void produce(FeedProduction production) {
        // 1. Save production record
        feedProductionRepository.save(production);

        // 2. Deduct inputs from stock
        // Logic: Input Required = (TotalQuantity * ItemPercentage) / 100
        if (production.getFormula() != null && production.getFormula().getItems() != null) {
            for (FeedFormulaItem item : production.getFormula().getItems()) {
                double requiredAmount = (production.getTotalQuantityKg() * item.getPercentage()) / 100.0;

                FeedInput input = item.getInput();
                // Fetch fresh stock
                input = feedInputRepository.findById(input.getId()).orElseThrow(() -> new RuntimeException("Insumo não encontrado"));

                if (input.getStockQuantityKg() < requiredAmount) {
                     throw new RuntimeException("Estoque insuficiente para insumo: " + input.getName());
                }

                input.setStockQuantityKg(input.getStockQuantityKg() - requiredAmount);
                feedInputRepository.save(input);
            }
        }
    }
}
