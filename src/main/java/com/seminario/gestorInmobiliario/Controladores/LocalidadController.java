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
import org.springframework.web.bind.annotation.ResponseBody;

import com.seminario.gestorInmobiliario.Entidades.Localidad;
import com.seminario.gestorInmobiliario.Servicios.LocalidadService;
import com.seminario.gestorInmobiliario.Servicios.ProvinciaServicio;

@Controller
@RequestMapping("/localidad")
public class LocalidadController {

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private ProvinciaServicio provinciaServicio;

    @GetMapping("/listaProv")
    @ResponseBody
    public List<Localidad> obtenerLocalidadesPorProvincia(@RequestParam int idProvincia) {
        return localidadService.buscarPorProvincia(idProvincia);
    }

    @GetMapping("/registrar") // localhost:8080/localidad/registrar
    public String registrar(ModelMap model) {

        model.addAttribute("provincias", provinciaServicio.listarProvincias());
        return "localidad/form";
    }
    
    @PostMapping("/registro")
    public String registrarLocalidad(
            @RequestParam Integer idProvincia,
            @RequestParam String nombre,
            @RequestParam Integer codPostal,
            ModelMap model) {

        try {
            Localidad localidadRegistrada = localidadService.crearLocalidad(nombre, codPostal, idProvincia);
            
            model.put("exito", "La localidad fue registrada exitosamente");
            model.put("localidadRegistrada",localidadRegistrada.getIdLocalidad());

        } catch (Exception e) {
            model.put("error", e.getMessage());
        }

        model.addAttribute("provincias", provinciaServicio.listarProvincias());
        return "localidad/form";
    }

    @GetMapping("/listar")
    public String listarLocalidades(@RequestParam(required = false) String provincia, Model model) {
        try {
            List<Localidad> localidades = localidadService.listarLocalidades();

            if (provincia != null && !provincia.isEmpty()) {
                localidades = localidadService.listarLocalidadesPorProvincia(provincia);
            } else {
                localidades = localidadService.listarLocalidades();
            }
            
            model.addAttribute("localidades", localidades);
            model.addAttribute("provinciaSeleccionada", provincia);
            return "localidad/listarLocalidades";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar agentes: " + e.getMessage());
            return "localidad/listarLocalidades";
        }
    }
}


    


