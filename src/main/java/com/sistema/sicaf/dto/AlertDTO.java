package com.sistema.sicaf.dto;

import com.sistema.sicaf.model.Expense;
import com.sistema.sicaf.model.FeedInput;
import com.sistema.sicaf.model.Sale;
import lombok.Data;
import java.util.List;

@Data
public class AlertDTO {
    private List<Expense> overdueExpenses;
    private List<Sale> overdueSales;
    private List<FeedInput> lowStockInputs;
    private int totalAlerts;
}
