package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.FeedDistribution;
import com.sistema.sicaf.service.FeedDistributionService;
import com.sistema.sicaf.service.FeedFormulaService;
import com.sistema.sicaf.service.PastureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/feed/distribution")
public class FeedDistributionController {

    @Autowired
    private FeedDistributionService feedDistributionService;

    @Autowired
    private PastureService pastureService;

    @Autowired
    private FeedFormulaService feedFormulaService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("distributions", feedDistributionService.findAll());
        return "feed/distribution/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        FeedDistribution distribution = new FeedDistribution();
        distribution.setDate(LocalDate.now());

        model.addAttribute("distribution", distribution);
        model.addAttribute("pastures", pastureService.findAll());
        model.addAttribute("formulas", feedFormulaService.findAll());
        return "feed/distribution/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("distribution") FeedDistribution distribution, RedirectAttributes redirectAttributes) {
        feedDistributionService.save(distribution);
        redirectAttributes.addFlashAttribute("successMessage", "Fornecimento registrado com sucesso!");
        return "redirect:/feed/distribution";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        feedDistributionService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Registro removido.");
        return "redirect:/feed/distribution";
    }
}
