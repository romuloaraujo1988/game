package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Expense;
import com.sistema.sicaf.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("expenses", expenseService.findAll());
        return "expenses/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "expenses/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("expense") Expense expense, RedirectAttributes redirectAttributes) {
        expenseService.save(expense);
        redirectAttributes.addFlashAttribute("successMessage", "Despesa registrada com sucesso!");
        return "redirect:/expenses";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return expenseService.findById(id).map(expense -> {
            model.addAttribute("expense", expense);
            return "expenses/form";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Despesa não encontrada.");
            return "redirect:/expenses";
        });
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        expenseService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Despesa removida.");
        return "redirect:/expenses";
    }
}
