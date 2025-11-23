package com.sistema.sicaf.service;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.AnimalStatus;
import com.sistema.sicaf.model.Sale;
import com.sistema.sicaf.model.SaleItem;
import com.sistema.sicaf.model.PaymentStatus;
import com.sistema.sicaf.repository.AnimalRepository;
import com.sistema.sicaf.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public List<Sale> findPending() {
        return saleRepository.findByStatusOrderByDueDateAsc(PaymentStatus.PENDING);
    }

    @Transactional
    public Sale save(Sale sale) {
        if (sale.getStatus() == null) {
            sale.setStatus(PaymentStatus.PENDING);
        }

        if (sale.getItems() != null) {
            for (SaleItem item : sale.getItems()) {
                item.setSale(sale);
                if (item.getAnimal() != null) {
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

    public void settleSale(Long id) {
        saleRepository.findById(id).ifPresent(sale -> {
            sale.setStatus(PaymentStatus.PAID);
            sale.setReceiptDate(LocalDate.now());
            saleRepository.save(sale);
        });
    }

    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }
}
