package com.sistema.sicaf.controller;

import com.sistema.sicaf.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("dashboard", dashboardService.getDashboardData());
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
