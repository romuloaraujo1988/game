package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.FeedProduction;
import com.sistema.sicaf.service.FeedFormulaService;
import com.sistema.sicaf.service.FeedProductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/feed/production")
public class FeedProductionController {

    @Autowired
    private FeedProductionService feedProductionService;

    @Autowired
    private FeedFormulaService feedFormulaService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("productions", feedProductionService.findAll());
        return "feed/production/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        FeedProduction production = new FeedProduction();
        production.setDate(LocalDate.now());

        model.addAttribute("production", production);
        model.addAttribute("formulas", feedFormulaService.findAll());
        return "feed/production/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("production") FeedProduction production, RedirectAttributes redirectAttributes) {
        try {
            feedProductionService.produce(production);
            redirectAttributes.addFlashAttribute("successMessage", "Produção registrada! Estoque de insumos atualizado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro na produção: " + e.getMessage());
            return "redirect:/feed/production/new";
        }
        return "redirect:/feed/production";
    }
}
