package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Entidades.Provincia;
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
            Provincia provinciaRegistrada = provinciaServicio.crearProvincia(nombre);

            model.put("exito", "La provincia fue registrada exitosamente"); 
            model.put("idProvinciaRegistrada", provinciaRegistrada.getIdProvincia());

        } catch (Exception ex) {
            model.put("error", ex.getMessage());
        }
        return "provincia/form";
    }


    @GetMapping("/listar")
    public String listarProvincias(Model model) {
        try {
            List<Provincia> provincias = provinciaServicio.listarProvincias();
            model.addAttribute("provincias", provincias);
            return "provincia/listarProvincias";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar agentes: " + e.getMessage());
            return "provincia/listarProvincia";
        }
    }
}


