package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.AnimalStatus;
import com.sistema.sicaf.model.Pasture;
import com.sistema.sicaf.repository.AnimalRepository;
import com.sistema.sicaf.repository.PastureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private AnimalRepository animalRepository;

    @GetMapping
    public String index() {
        return "reports/index";
    }

    @GetMapping("/inventory")
    public String inventory(Model model) {
        // Fetch all active animals
        List<Animal> activeAnimals = animalRepository.findByStatusOrderByPastureNameAsc(AnimalStatus.ACTIVE);

        // Group by Pasture Name (or "Sem Pasto")
        Map<String, List<Animal>> animalsByPasture = activeAnimals.stream()
            .collect(Collectors.groupingBy(a -> a.getPasture() != null ? a.getPasture().getName() : "Sem Pasto Definido"));

        model.addAttribute("animalsByPasture", animalsByPasture);
        model.addAttribute("totalAnimals", activeAnimals.size());
        model.addAttribute("generationDate", LocalDateTime.now());

        return "reports/inventory";
    }
}
