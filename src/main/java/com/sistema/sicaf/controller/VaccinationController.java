package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.Animal;
import com.sistema.sicaf.model.Vaccination;
import com.sistema.sicaf.service.AnimalService;
import com.sistema.sicaf.service.VaccinationService;
import com.sistema.sicaf.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/vaccinations")
public class VaccinationController {

    @Autowired
    private VaccinationService vaccinationService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private VaccineService vaccineService;

    @GetMapping("/history/{animalId}")
    public String history(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        }
        model.addAttribute("animal", animal.get());
        model.addAttribute("vaccinations", vaccinationService.findByAnimalId(animalId));
        return "vaccinations/history";
    }

    @GetMapping("/new/{animalId}")
    public String createForm(@PathVariable Long animalId, Model model, RedirectAttributes redirectAttributes) {
        Optional<Animal> animal = animalService.findById(animalId);
        if (animal.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Animal não encontrado.");
            return "redirect:/animals";
        }
        Vaccination vaccination = new Vaccination();
        vaccination.setAnimal(animal.get());

        model.addAttribute("vaccination", vaccination);
        model.addAttribute("animal", animal.get());
        model.addAttribute("availableVaccines", vaccineService.findAll());
        return "vaccinations/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("vaccination") Vaccination vaccination, RedirectAttributes redirectAttributes) {
        vaccinationService.save(vaccination);
        redirectAttributes.addFlashAttribute("successMessage", "Vacinação registrada com sucesso!");
        return "redirect:/vaccinations/history/" + vaccination.getAnimal().getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Vaccination> vaccination = vaccinationService.findById(id);
        if (vaccination.isPresent()) {
            Long animalId = vaccination.get().getAnimal().getId();
            vaccinationService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Vacinação removida.");
            return "redirect:/vaccinations/history/" + animalId;
        }
        return "redirect:/animals";
    }
}
