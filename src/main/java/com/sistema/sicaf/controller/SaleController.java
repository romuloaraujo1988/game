package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.AnimalStatus;
import com.sistema.sicaf.model.Sale;
import com.sistema.sicaf.model.SaleItem;
import com.sistema.sicaf.service.AnimalService;
import com.sistema.sicaf.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sales")
public class SaleController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private AnimalService animalService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("sales", saleService.findAll());
        return "sales/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Sale sale = new Sale();
        sale.setDate(LocalDate.now());
        model.addAttribute("sale", sale);

        // Filter only ACTIVE animals
        List<Animal> activeAnimals = animalService.findAll().stream()
                .filter(a -> a.getStatus() == AnimalStatus.ACTIVE)
                .collect(Collectors.toList());
        model.addAttribute("activeAnimals", activeAnimals);

        return "sales/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("sale") Sale sale,
                       @RequestParam(value = "animalIds", required = false) List<Long> animalIds,
                       @RequestParam(value = "prices", required = false) List<BigDecimal> prices,
                       RedirectAttributes redirectAttributes) {

        if (animalIds == null || animalIds.isEmpty()) {
             redirectAttributes.addFlashAttribute("errorMessage", "Selecione pelo menos um animal.");
             return "redirect:/sales/new";
        }

        List<SaleItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < animalIds.size(); i++) {
            Long animalId = animalIds.get(i);
            BigDecimal price = (prices != null && prices.size() > i) ? prices.get(i) : BigDecimal.ZERO;

            SaleItem item = new SaleItem();
            Animal animal = new Animal();
            animal.setId(animalId);

            item.setAnimal(animal);
            item.setPrice(price);
            items.add(item);

            total = total.add(price);
        }

        sale.setItems(items);
        sale.setTotalAmount(total);

        saleService.save(sale);

        redirectAttributes.addFlashAttribute("successMessage", "Venda registrada com sucesso!");
        return "redirect:/sales";
    }
}
