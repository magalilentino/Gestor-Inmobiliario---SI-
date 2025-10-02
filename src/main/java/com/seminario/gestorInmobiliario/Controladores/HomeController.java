package com.seminario.gestorInmobiliario.Controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
<<<<<<< HEAD
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import jakarta.servlet.http.HttpSession;

=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

>>>>>>> origin/rama-deque
@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
<<<<<<< HEAD
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
=======
    public String home(Model model) {
        model.addAttribute("titulo", "Gestor Inmobiliario");
        model.addAttribute("mensaje", "Bienvenido al sistema de gestión inmobiliaria");
>>>>>>> origin/rama-deque
        return "index";
    }

    @GetMapping("/propiedades")
    public String propiedades() {
        return "propiedades/lista";
    }
<<<<<<< HEAD
=======

    @GetMapping("/login")
    public String login() {
        return "login";
    }
>>>>>>> origin/rama-deque
}