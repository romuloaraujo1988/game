package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.FeedInput;
import com.sistema.sicaf.service.FeedInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/feed/inputs")
public class FeedInputController {

    @Autowired
    private FeedInputService feedInputService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("inputs", feedInputService.findAll());
        return "feed/inputs/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("input", new FeedInput());
        return "feed/inputs/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("input") FeedInput input, RedirectAttributes redirectAttributes) {
        feedInputService.save(input);
        redirectAttributes.addFlashAttribute("successMessage", "Insumo salvo com sucesso!");
        return "redirect:/feed/inputs";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return feedInputService.findById(id).map(input -> {
            model.addAttribute("input", input);
            return "feed/inputs/form";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Insumo não encontrado.");
            return "redirect:/feed/inputs";
        });
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            feedInputService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Insumo removido.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Não é possível remover insumo em uso.");
        }
        return "redirect:/feed/inputs";
    }
}
