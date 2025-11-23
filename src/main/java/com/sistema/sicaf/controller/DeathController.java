package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.Death;
import com.sistema.sicaf.service.AnimalService;
import com.sistema.sicaf.service.DeathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/deaths")
public class DeathController {

    @Autowired
    private DeathService deathService;

    @Autowired
    private AnimalService animalService;

    @GetMapping("/new/{animalId}")
    public String createForm(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        }

        Death death = new Death();
        death.setAnimal(animal.get());

        model.addAttribute("death", death);
        model.addAttribute("animal", animal.get());

        return "deaths/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("death") Death death, RedirectAttributes redirectAttributes) {
        deathService.save(death);
        redirectAttributes.addFlashAttribute("successMessage", "Óbito registrado com sucesso. Animal removido do estoque ativo.");
        return "redirect:/animals";
    }
}
