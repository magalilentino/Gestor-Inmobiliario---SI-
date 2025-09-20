package com.seminario.gestorInmobiliario.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Servicios.AgenteServicios;

import jakarta.servlet.http.HttpSession;


public class LoginController {


    @Autowired
    private AgenteServicios agenteService;

    // Página principal de login
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", error);
        }
        return "login"; // login.html en templates
    }

    // Procesar login
    @PostMapping("/login")
    public String ingresar(@RequestParam String usuario,
                           @RequestParam String clave,
                           HttpSession session,
                           ModelMap modelo) {
        try {
            Agente agente = agenteService.login(usuario, clave);
            session.setAttribute("agentesession", agente); // guardar en sesión
            return "redirect:/inicio";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("usuario", usuario);
            return "login";
        }
    }

    // Página de registro
    @GetMapping("/registro")
    public String registro() {
        return "registro"; // registro.html en templates
    }

    // Procesar registro
    @PostMapping("/registro")
    public String registrar(@RequestParam String nombreApellido,
                            @RequestParam String dni,
                            @RequestParam String email,
                            @RequestParam String telefono,
                            @RequestParam String matricula,
                            @RequestParam String usuario,
                            @RequestParam String clave,
                            ModelMap modelo) {
        try {
            agenteService.crearAgente(dni, nombreApellido, email, telefono, matricula, usuario, clave);
            modelo.put("exito", "Agente registrado correctamente!");
            return "login"; // vuelve al login
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("nombreApellido", nombreApellido);
            modelo.put("dni", dni);
            modelo.put("email", email);
            modelo.put("usuario", usuario);
            return "registro";
        }
    }

    // Página de inicio (solo para agentes logueados)
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        Agente agente = (Agente) session.getAttribute("agentesession");
        if (agente == null) {
            return "redirect:/login?error=Debes iniciar sesión";
        }
        return "inicio"; // inicio.html en templates
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
