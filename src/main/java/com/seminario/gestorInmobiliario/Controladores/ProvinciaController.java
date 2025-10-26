package com.seminario.gestorInmobiliario.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Servicios.ProvinciaServicio;

@Controller
@RequestMapping("/provincia")
public class ProvinciaController {

    @Autowired
    private ProvinciaServicio provinciaServicio;

    
    @GetMapping("/registrar")
    public String crearInquilino() {
        return "provincia/form";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, 
                            ModelMap model){
        try {
            
            provinciaServicio.crearProvincia(nombre);
            model.put("exito", "La provincia fue cargada correctamente."); 
            
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
        }
        return "provincia/form";
    }
}


