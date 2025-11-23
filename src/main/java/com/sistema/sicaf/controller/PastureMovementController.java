package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.PastureMovement;
import com.sistema.sicaf.service.AnimalService;
import com.sistema.sicaf.service.PastureMovementService;
import com.sistema.sicaf.service.PastureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/movements")
public class PastureMovementController {

    @Autowired
    private PastureMovementService movementService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private PastureService pastureService;

    @GetMapping("/history/{animalId}")
    public String history(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        }

        model.addAttribute("animal", animal.get());
        model.addAttribute("movements", movementService.findByAnimalId(animalId));
        return "movements/history";
    }

    @GetMapping("/new/{animalId}")
    public String createForm(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        }

        PastureMovement movement = new PastureMovement();
        movement.setAnimal(animal.get());
        movement.setOriginPasture(animal.get().getPasture()); // Set current pasture as origin
        movement.setDate(LocalDate.now());

        model.addAttribute("movement", movement);
        model.addAttribute("animal", animal.get());
        model.addAttribute("pastures", pastureService.findAll()); // List all pastures for destination

        return "movements/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("movement") PastureMovement movement, RedirectAttributes redirectAttributes) {
        // Validation: Destination cannot be same as Origin (optional, but good practice)
        if (movement.getOriginPasture() != null &&
            movement.getOriginPasture().getId().equals(movement.getDestinationPasture().getId())) {
             redirectAttributes.addFlashAttribute("errorMessage", "O pasto de destino deve ser diferente do atual.");
             return "redirect:/movements/new/" + movement.getAnimal().getId();
        }

        movementService.save(movement);
        redirectAttributes.addFlashAttribute("successMessage", "Movimentação realizada com sucesso!");
        return "redirect:/animals"; // Redirect to animal list to see updated pasture
    }
}
