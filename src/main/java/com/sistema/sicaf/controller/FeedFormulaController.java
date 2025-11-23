package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.FeedFormula;
import com.sistema.sicaf.model.FeedFormulaItem;
import com.sistema.sicaf.model.FeedInput;
import com.sistema.sicaf.service.FeedFormulaService;
import com.sistema.sicaf.service.FeedInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/feed/formulas")
public class FeedFormulaController {

    @Autowired
    private FeedFormulaService feedFormulaService;

    @Autowired
    private FeedInputService feedInputService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("formulas", feedFormulaService.findAll());
        return "feed/formulas/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        FeedFormula formula = new FeedFormula();
        // Pre-add a few empty items for the UI
        formula.setItems(new ArrayList<>());

        model.addAttribute("formula", formula);
        model.addAttribute("inputs", feedInputService.findAll());
        return "feed/formulas/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("formula") FeedFormula formula,
                       @RequestParam(value = "inputIds", required = false) List<Long> inputIds,
                       @RequestParam(value = "percentages", required = false) List<Double> percentages,
                       RedirectAttributes redirectAttributes) {

        List<FeedFormulaItem> items = new ArrayList<>();
        if (inputIds != null) {
            for (int i = 0; i < inputIds.size(); i++) {
                if (inputIds.get(i) != null && percentages.get(i) != null) {
                    FeedFormulaItem item = new FeedFormulaItem();
                    FeedInput input = new FeedInput();
                    input.setId(inputIds.get(i));

                    item.setInput(input);
                    item.setPercentage(percentages.get(i));
                    items.add(item);
                }
            }
        }
        formula.setItems(items);

        feedFormulaService.save(formula);
        redirectAttributes.addFlashAttribute("successMessage", "Fórmula salva com sucesso!");
        return "redirect:/feed/formulas";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return feedFormulaService.findById(id).map(formula -> {
            model.addAttribute("formula", formula);
            model.addAttribute("inputs", feedInputService.findAll());
            return "feed/formulas/form";
        }).orElseGet(() -> {
            redirectAttributes.addFlashAttribute("errorMessage", "Fórmula não encontrada.");
            return "redirect:/feed/formulas";
        });
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        feedFormulaService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Fórmula removida.");
        return "redirect:/feed/formulas";
    }
}
