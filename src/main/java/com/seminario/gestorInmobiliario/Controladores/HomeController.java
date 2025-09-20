package com.seminario.gestorInmobiliario.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("titulo", "Gestor Inmobiliario");
        model.addAttribute("mensaje", "Bienvenido al sistema de gestión inmobiliaria");
        return "index";
    }

    // @GetMapping("/agentes")
    // public String agentes() {
    //     return "agentes/lista";
    // }

    @GetMapping("/propiedades")
    public String propiedades() {
        return "propiedades/lista";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}