package com.sistema.sicaf.dto;

import com.sistema.sicaf.model.Animal;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class DashboardDTO {
    private long totalActiveAnimals;
    private long activeMales;
    private long activeFemales;
    private BigDecimal monthlySales;
    private BigDecimal monthlyExpenses; // Added
    private BigDecimal monthlyProfit;   // Added
    private double occupancyRate;
    private List<Animal> recentAnimals;
}
