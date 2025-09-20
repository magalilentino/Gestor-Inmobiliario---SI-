package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Servicios.AgenteServicios;

@Controller
@RequestMapping("/agentes")
public class AgenteController {

    @Autowired
    private AgenteServicios agenteServicios;

    @GetMapping
    public String listarAgentes(Model model) {
        try {
            List<Agente> agentes = agenteServicios.listarAgentes();
            model.addAttribute("agentes", agentes);
            return "agentes/lista";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar agentes: " + e.getMessage());
            return "agentes/lista";
        }
    }

    @GetMapping("/nuevo")
    public String nuevoAgente(Model model) {
        model.addAttribute("agente", new Agente());
        return "agentes/formulario";
    }

    @PostMapping("/guardar")
    public String guardarAgente(@RequestParam String dniAgente,
                              @RequestParam String nomApe,
                              @RequestParam String email,
                              @RequestParam String telefono,
                              @RequestParam String matricula,
                              @RequestParam String usuario,
                              @RequestParam String clave,
                              RedirectAttributes redirectAttributes) {
        try {
            agenteServicios.crearAgente(dniAgente, nomApe, email, telefono, matricula, usuario, clave);
            redirectAttributes.addFlashAttribute("exito", "Agente creado correctamente");
            return "redirect:/agentes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear agente: " + e.getMessage());
            return "redirect:/agentes/nuevo";
        }
    }

    @GetMapping("/editar/{dni}")
    public String editarAgente(@PathVariable String dni, Model model) {
        try {
            Agente agente = agenteServicios.getOne(dni);
            model.addAttribute("agente", agente);
            return "agentes/formulario";
        } catch (Exception e) {
            model.addAttribute("error", "Agente no encontrado");
            return "redirect:/agentes";
        }
    }

    @PostMapping("/actualizar")
    public String actualizarAgente(@RequestParam String dniAgente,
                                 @RequestParam String nomApe,
                                 @RequestParam String email,
                                 @RequestParam String telefono,
                                 @RequestParam String matricula,
                                 @RequestParam String usuario,
                                 @RequestParam String clave,
                                 RedirectAttributes redirectAttributes) {
        try {
            agenteServicios.modificarAgente(dniAgente, nomApe, email, telefono, matricula, usuario, clave);
            redirectAttributes.addFlashAttribute("exito", "Agente actualizado correctamente");
            return "redirect:/agentes";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar agente: " + e.getMessage());
            return "redirect:/agentes";
        }
    }

    @GetMapping("/eliminar/{dni}")
    public String eliminarAgente(@PathVariable String dni, RedirectAttributes redirectAttributes) {
        try {
            agenteServicios.eliminarAgente(dni);
            redirectAttributes.addFlashAttribute("exito", "Agente eliminado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar agente: " + e.getMessage());
        }
        return "redirect:/agentes";
    }
}