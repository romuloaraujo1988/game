package com.sistema.sicaf.controller;

import com.sistema.sicaf.model.ERole;
import com.sistema.sicaf.model.Role;
import com.sistema.sicaf.model.User;
import com.sistema.sicaf.repository.RoleRepository;
import com.sistema.sicaf.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "users/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") User user, @RequestParam List<Long> roleIds, RedirectAttributes redirectAttributes) {
        if (userRepository.existsByUsername(user.getUsername())) {
             redirectAttributes.addFlashAttribute("errorMessage", "Erro: Nome de usuário já existe!");
             return "redirect:/users/new";
        }

        user.setPassword(encoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            roleRepository.findById(roleId).ifPresent(roles::add);
        }
        user.setRoles(roles);

        userRepository.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
        return "redirect:/users";
    }
}
