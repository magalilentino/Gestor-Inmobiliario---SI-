package com.seminario.gestorInmobiliario.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Servicios.AgenteServicios;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private AgenteServicios agenteService;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // Página principal después del login
    @GetMapping("/index")
    public String index(Authentication auth, ModelMap modelo) {
        Agente agente = agenteService.getByUser(auth.getName());
        if (agente == null) {
            return "redirect:/login";
        }

        modelo.put("agente", agente);
        modelo.put("titulo", "Gestor Inmobiliario");
        modelo.put("mensaje", "Bienvenido al sistema de gestión inmobiliaria");
        return "index";
    }

    @GetMapping("/propiedades")
    public String propiedades(Authentication auth) {
        Agente agente = agenteService.getByUser(auth.getName());
        if (agente == null) {
            return "redirect:/login";
        }
        return "propiedades/lista";
    }

    @GetMapping("/visitas")
    public String visitas(Authentication auth, ModelMap modelo) {
        Agente agente = agenteService.getByUser(auth.getName());
        if (agente == null) {
            return "redirect:/login";
        }

        modelo.put("agente", agente);
        return "visitas/agendar-visitas";
    }

    @GetMapping("/categorias")
    public String categorias(Authentication auth, ModelMap modelo) {
        Agente agente = agenteService.getByUser(auth.getName());
        if (agente == null) {
            return "redirect:/login";
        }

        modelo.put("agente", agente);
        return "categorias/lista";
    }
}
