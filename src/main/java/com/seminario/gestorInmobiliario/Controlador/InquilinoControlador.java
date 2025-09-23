package com.seminario.gestorInmobiliario.Controlador;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Servicios.InquilinoService;

@Controller
@RequestMapping("/inquilino")
public class InquilinoControlador {
    @Autowired
    private InquilinoService inquilinoService;
    
    @GetMapping("/registrar") // localhost:8080/inwuilino/registrar
    public String registrar() {
        return "inquilino/form";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String dni, 
                            @RequestParam String nomApe, 
                            @RequestParam String telefono,  
                            @RequestParam String email, ModelMap model){
        try {

            inquilinoService.crearInquilino(dni, nomApe, email, telefono);
            model.put("exito", "El Inquilino fue cargado correctamente.");
            

        } catch (Exception ex) {
            model.put("error", ex.getMessage());

            return "inquilino/form"; 
        }
        return "alquiler/buscarInquilino";
    }

}
