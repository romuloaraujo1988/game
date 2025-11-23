package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Pasture;
import com.sistema.sicaf.service.PastureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pastures")
public class PastureController {

    @Autowired
    private PastureService pastureService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("pastures", pastureService.findAll());
        return "pastures/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("pasture", new Pasture());
        return "pastures/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("pasture") Pasture pasture, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "pastures/form";
        }
        pastureService.save(pasture);
        redirectAttributes.addFlashAttribute("successMessage", "Pasto salvo com sucesso!");
        return "redirect:/pastures";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return pastureService.findById(id).map(pasture -> {
            model.addAttribute("pasture", pasture);
            return "pastures/form";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Pasto não encontrado.");
            return "redirect:/pastures";
        });
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pastureService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Pasto removido com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Não é possível remover este pasto pois existem animais vinculados.");
        }
        return "redirect:/pastures";
    }
}
