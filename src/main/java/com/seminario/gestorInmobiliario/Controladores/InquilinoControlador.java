package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import com.seminario.gestorInmobiliario.Servicios.InquilinoService;

@Controller
@RequestMapping("/inquilino")
public class InquilinoControlador {

    @Autowired
    private InquilinoService inquilinoServicio;

    @GetMapping("/registrar")
    public String crearInquilino() {
        return "inquilino/form";
    }

      @PostMapping("/registro")
    public String registro(@RequestParam String dni, 
                            @RequestParam String nomApe, 
                            @RequestParam String telefono,  
                            @RequestParam String email, ModelMap model){
        try {

            inquilinoServicio.crearInquilino(dni, nomApe, email, telefono);
            model.put("exito", "El Inquilino fue cargado correctamente.");
            

        } catch (Exception ex) {
            model.put("error", ex.getMessage());

            return "inquilino/form"; 
        }
        return "alquiler/buscarInquilino";
    }
    // @PostMapping("/registro")
    // public String guardarInquilino(@RequestParam String nombre,
    //         @RequestParam String dni, @RequestParam String telefono, @RequestParam String email,
    //         @RequestParam String garantia, ModelMap modelo) {
    //     try {
    //         inquilinoServicio.crearInquilino(dni, nombre, email, telefono);
    //         modelo.put("exito", "El inquilino fue registrado correctamente!");
    //         return "redirect:/";
    //     } catch (Exception e) {
    //         modelo.put("error", e.getMessage());
    //         return "inquilino/form";
    //     }
    // }

    @GetMapping("/listar")
    public String listarInquilinos(ModelMap modelo) {
        List<Inquilino> inquilinos = inquilinoServicio.listarInquilinos();
        modelo.addAttribute("inquilinos", inquilinos);
        return "inquilino/listar";
    }
}