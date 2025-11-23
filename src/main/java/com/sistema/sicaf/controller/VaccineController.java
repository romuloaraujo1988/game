package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Vaccine;
import com.sistema.sicaf.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/vaccines")
public class VaccineController {

    @Autowired
    private VaccineService vaccineService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("vaccines", vaccineService.findAll());
        return "vaccines/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("vaccine", new Vaccine());
        return "vaccines/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("vaccine") Vaccine vaccine, RedirectAttributes redirectAttributes) {
        vaccineService.save(vaccine);
        redirectAttributes.addFlashAttribute("successMessage", "Vacina salva com sucesso!");
        return "redirect:/vaccines";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return vaccineService.findById(id).map(vaccine -> {
            model.addAttribute("vaccine", vaccine);
            return "vaccines/form";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Vacina não encontrada.");
            return "redirect:/vaccines";
        });
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            vaccineService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Vacina removida com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Não é possível remover vacina em uso.");
        }
        return "redirect:/vaccines";
    }
}
