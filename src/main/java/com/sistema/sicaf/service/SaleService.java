package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.AnimalStatus;
import com.sistema.sicaf.model.Sale;
import com.sistema.sicaf.model.SaleItem;
import com.sistema.sicaf.repository.AnimalRepository;
import com.sistema.sicaf.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private AnimalRepository animalRepository;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Transactional
    public Sale save(Sale sale) {
        // Recalculate total and update animal status
        // Note: For simplicity in this scaffold, we assume the Sale object comes with Items populated
        // but often items need careful handling.

        if (sale.getItems() != null) {
            for (SaleItem item : sale.getItems()) {
                item.setSale(sale);
                // Update Animal Status
                if (item.getAnimal() != null) {
                    // Fetch fresh entity to ensure we are updating DB
                   Animal animal = animalRepository.findById(item.getAnimal().getId()).orElse(null);
                   if (animal != null) {
                       animal.setStatus(AnimalStatus.SOLD);
                       animalRepository.save(animal);
                   }
                }
            }
        }

        return saleRepository.save(sale);
    }

    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }
}
