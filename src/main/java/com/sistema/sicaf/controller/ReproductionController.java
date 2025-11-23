package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.Gender;
import com.sistema.sicaf.model.Reproduction;
import com.sistema.sicaf.service.AnimalService;
import com.sistema.sicaf.service.ReproductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/reproductions")
public class ReproductionController {

    @Autowired
    private ReproductionService reproductionService;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/history/{animalId}")
    public String history(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        }
        if (animal.get().getGender() != Gender.FEMALE) {
            redirectAttributes.addFlashAttribute("errorMessage", "Reprodução permitida apenas para fêmeas.");
            return "redirect:/animals";
        }

        model.addAttribute("animal", animal.get());
        model.addAttribute("reproductions", reproductionService.findByDamId(animalId));
        return "reproductions/history";
    }

    @GetMapping("/new/{animalId}")
    public String createForm(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty() || animal.get().getGender() != Gender.FEMALE) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ação inválida para este animal.");
            return "redirect:/animals";
        }

        Reproduction reproduction = new Reproduction();
        reproduction.setDam(animal.get());

        model.addAttribute("reproduction", reproduction);
        model.addAttribute("animal", animal.get());
        return "reproductions/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("reproduction") Reproduction reproduction, RedirectAttributes redirectAttributes) {
        reproductionService.save(reproduction);
        redirectAttributes.addFlashAttribute("successMessage", "Evento reprodutivo registrado!");
        return "redirect:/reproductions/history/" + reproduction.getDam().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Reproduction> reproduction = reproductionService.findById(id);
        if (reproduction.isPresent()) {
            Long damId = reproduction.get().getDam().getId();
            reproductionService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Evento removido.");
            return "redirect:/reproductions/history/" + damId;
        }
        return "redirect:/animals";
    }
}
