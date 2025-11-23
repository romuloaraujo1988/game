package com.sistema.sicaf.controller;

import com.sistema.sicaf.dto.AnimalDossierDTO;
import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.AnimalStatus;
import com.sistema.sicaf.model.Sale;
import com.sistema.sicaf.repository.AnimalRepository;
import com.sistema.sicaf.repository.SaleRepository;
import com.sistema.sicaf.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String index() {
        return "reports/index";
    }

    @GetMapping("/inventory")
    public String inventory(Model model) {
        List<Animal> activeAnimals = animalRepository.findByStatusOrderByPastureNameAsc(AnimalStatus.ACTIVE);

        Map<String, List<Animal>> animalsByPasture = activeAnimals.stream()
            .collect(Collectors.groupingBy(a -> a.getPasture() != null ? a.getPasture().getName() : "Sem Pasto Definido"));

        model.addAttribute("animalsByPasture", animalsByPasture);
        model.addAttribute("totalAnimals", activeAnimals.size());
        model.addAttribute("generationDate", LocalDateTime.now());

        return "reports/inventory";
    }

    // --- Animal Dossier ---

    @GetMapping("/animal-search")
    public String animalSearch() {
        return "reports/animal_search";
    }

    @PostMapping("/animal-dossier")
    public String findAnimalForDossier(@RequestParam("earTag") String earTag, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalRepository.findByEarTag(earTag);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado com o brinco: " + earTag);
            return "redirect:/reports/animal-search";
        }
        return "redirect:/reports/animal-dossier/" + animal.get().getId();
    }

    @GetMapping("/animal-dossier/{id}")
    public String animalDossier(@PathVariable Long id, Model model) {
        AnimalDossierDTO dossier = reportService.generateAnimalDossier(id);
        model.addAttribute("dossier", dossier);
        model.addAttribute("generationDate", LocalDateTime.now());
        return "reports/animal_dossier";
    }

    // --- Sales Report ---

    @GetMapping("/sales-period")
    public String salesPeriodForm() {
        return "reports/sales_period_form";
    }

    @GetMapping("/sales-period/result")
    public String salesPeriodResult(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                    @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                    Model model) {
        List<Sale> sales = saleRepository.findByDateBetween(startDate, endDate);

        // Calculate totals
        java.math.BigDecimal totalRevenue = sales.stream()
                .map(Sale::getTotalAmount)
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

        model.addAttribute("sales", sales);
        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("generationDate", LocalDateTime.now());

        return "reports/sales_period_result";
    }
}
