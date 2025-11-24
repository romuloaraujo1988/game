package com.sistema.sicaf.service;

import com.sistema.sicaf.dto.DashboardDTO;
import com.sistema.sicaf.model.AnimalStatus;
import com.sistema.sicaf.model.Gender;
import com.sistema.sicaf.repository.AnimalRepository;
import com.sistema.sicaf.repository.ExpenseRepository;
import com.sistema.sicaf.repository.PastureRepository;
import com.sistema.sicaf.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class DashboardService {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private PastureRepository pastureRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AlertService alertService;

    public DashboardDTO getDashboardData() {
        DashboardDTO dto = new DashboardDTO();

        // Animals Stats
        dto.setTotalActiveAnimals(animalRepository.countByStatus(AnimalStatus.ACTIVE));
        dto.setActiveMales(animalRepository.countByStatusAndGender(AnimalStatus.ACTIVE, Gender.MALE));
        dto.setActiveFemales(animalRepository.countByStatusAndGender(AnimalStatus.ACTIVE, Gender.FEMALE));
        dto.setRecentAnimals(animalRepository.findTop5ByOrderByIdDesc());

        // Sales Stats (Current Month)
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        BigDecimal sales = saleRepository.sumTotalAmountByDateBetween(startOfMonth, endOfMonth);
        dto.setMonthlySales(sales != null ? sales : BigDecimal.ZERO);

        // Expense Stats (Current Month)
        BigDecimal expenses = expenseRepository.sumTotalAmountByDateBetween(startOfMonth, endOfMonth);
        dto.setMonthlyExpenses(expenses != null ? expenses : BigDecimal.ZERO);

        // Profit
        dto.setMonthlyProfit(dto.getMonthlySales().subtract(dto.getMonthlyExpenses()));

        // Occupancy Rate
        Integer totalCapacity = pastureRepository.sumTotalCapacity();
        if (totalCapacity != null && totalCapacity > 0) {
            double rate = (double) dto.getTotalActiveAnimals() / totalCapacity * 100;
            dto.setOccupancyRate(Math.round(rate * 100.0) / 100.0);
        } else {
            dto.setOccupancyRate(0.0);
        }

        // Alerts
        dto.setAlerts(alertService.getAlerts());

        return dto;
    }
}
