package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.service.AnimalService;
import com.sistema.sicaf.service.PastureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private PastureService pastureService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("animals", animalService.findAll());
        return "animals/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("animal", new Animal());
        model.addAttribute("pastures", pastureService.findAll());
        return "animals/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("animal") Animal animal, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pastures", pastureService.findAll());
            return "animals/form";
        }
        try {
            animalService.save(animal);
            redirectAttributes.addFlashAttribute("successMessage", "Animal salvo com sucesso!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Erro ao salvar animal. Verifique se o brinco já existe.");
            model.addAttribute("pastures", pastureService.findAll());
            return "animals/form";
        }
        return "redirect:/animals";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return animalService.findById(id).map(animal -> {
            model.addAttribute("animal", animal);
            model.addAttribute("pastures", pastureService.findAll());
            return "animals/form";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        });
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            animalService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Animal removido com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao remover animal.");
        }
        return "redirect:/animals";
    }
}
