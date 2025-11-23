package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.Weighing;
import com.sistema.sicaf.service.AnimalService;
import com.sistema.sicaf.service.WeighingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/weighings")
public class WeighingController {

    @Autowired
    private WeighingService weighingService;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/history/{animalId}")
    public String history(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        }
        model.addAttribute("animal", animal.get());
        model.addAttribute("weighings", weighingService.findByAnimalId(animalId));
        return "weighings/history";
    }

    @GetMapping("/new/{animalId}")
    public String createForm(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        }
        Weighing weighing = new Weighing();
        weighing.setAnimal(animal.get());

        model.addAttribute("weighing", weighing);
        model.addAttribute("animal", animal.get());
        return "weighings/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("weighing") Weighing weighing, RedirectAttributes redirectAttributes) {
        weighingService.save(weighing);
        redirectAttributes.addFlashAttribute("successMessage", "Pesagem registrada com sucesso!");
        return "redirect:/weighings/history/" + weighing.getAnimal().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Weighing> weighing = weighingService.findById(id);
        if (weighing.isPresent()) {
            Long animalId = weighing.get().getAnimal().getId();
            weighingService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Pesagem removida.");
            return "redirect:/weighings/history/" + animalId;
        }
        return "redirect:/animals";
    }
}
