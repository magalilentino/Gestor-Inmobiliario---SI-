package com.seminario.gestorInmobiliario.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // Página principal después del login
    @GetMapping("/index")
    public String index(HttpSession session, ModelMap modelo) {
        Agente agente = (Agente) session.getAttribute("agentesession");
        if (agente == null) {
            return "redirect:/login";
        }
        
        modelo.put("agente", agente);
        modelo.put("titulo", "Gestor Inmobiliario");
        modelo.put("mensaje", "Bienvenido al sistema de gestión inmobiliaria");
        return "index";
    }

    @GetMapping("/propiedades")
    public String propiedades(HttpSession session) {
        Agente agente = (Agente) session.getAttribute("agentesession");
        if (agente == null) {
            return "redirect:/login";
        }
        return "propiedades/lista";
    }
}