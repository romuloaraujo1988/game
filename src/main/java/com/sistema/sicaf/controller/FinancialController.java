package com.sistema.sicaf.controller;

import com.sistema.sicaf.service.ExpenseService;
import com.sistema.sicaf.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/financial")
public class FinancialController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private SaleService saleService;

    @GetMapping("/payables")
    public String payables(Model model) {
        model.addAttribute("expenses", expenseService.findPending());
        return "financial/payables";
    }

    @GetMapping("/pay/{id}")
    public String pay(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        expenseService.settleExpense(id);
        redirectAttributes.addFlashAttribute("successMessage", "Conta paga com sucesso!");
        return "redirect:/financial/payables";
    }

    @GetMapping("/receivables")
    public String receivables(Model model) {
        model.addAttribute("sales", saleService.findPending());
        return "financial/receivables";
    }

    @GetMapping("/receive/{id}")
    public String receive(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        saleService.settleSale(id);
        redirectAttributes.addFlashAttribute("successMessage", "Recebimento confirmado!");
        return "redirect:/financial/receivables";
    }
}
