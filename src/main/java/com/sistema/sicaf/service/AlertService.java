package com.sistema.sicaf.service;

import com.sistema.sicaf.dto.AlertDTO;
import com.sistema.sicaf.model.PaymentStatus;
import com.sistema.sicaf.repository.ExpenseRepository;
import com.sistema.sicaf.repository.FeedInputRepository;
import com.sistema.sicaf.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AlertService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private FeedInputRepository feedInputRepository;

    public AlertDTO getAlerts() {
        AlertDTO dto = new AlertDTO();
        LocalDate warningDate = LocalDate.now().plusDays(3); // Due today or in next 3 days

        dto.setOverdueExpenses(expenseRepository.findByStatusAndDueDateLessThanEqualOrderByDueDateAsc(PaymentStatus.PENDING, warningDate));
        dto.setOverdueSales(saleRepository.findByStatusAndDueDateLessThanEqualOrderByDueDateAsc(PaymentStatus.PENDING, warningDate));

        // Low Stock (e.g., less than 500kg - simplistic threshold, could be dynamic per item)
        dto.setLowStockInputs(feedInputRepository.findByStockQuantityKgLessThan(500.0));

        dto.setTotalAlerts(
            (dto.getOverdueExpenses() != null ? dto.getOverdueExpenses().size() : 0) +
            (dto.getOverdueSales() != null ? dto.getOverdueSales().size() : 0) +
            (dto.getLowStockInputs() != null ? dto.getLowStockInputs().size() : 0)
        );

        return dto;
    }
}
