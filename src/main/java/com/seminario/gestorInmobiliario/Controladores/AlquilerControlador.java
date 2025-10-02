package com.seminario.gestorInmobiliario.Controladores;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import com.seminario.gestorInmobiliario.Servicios.AlquilerServicio;
import com.seminario.gestorInmobiliario.Servicios.PropiedadServicio;
import com.seminario.gestorInmobiliario.Servicios.InquilinoService;

@Controller
@RequestMapping("/alquiler")
public class AlquilerControlador {

    @Autowired
    private AlquilerServicio alquilerServicio;

    @Autowired
    private InquilinoService inquilinoServicio;

    @Autowired
    private PropiedadServicio propiedadServicio;

    @GetMapping("/registrar")
    public String crearAlquiler(ModelMap modelo) {
        List<Inquilino> inquilinos = inquilinoServicio.listarInquilinos();
        List<Propiedad> propiedades = propiedadServicio.listarPropiedades();
        modelo.addAttribute("inquilinos", inquilinos);
        modelo.addAttribute("propiedades", propiedades);
        return "alquiler/form";
    }

    @PostMapping("/registro")
    public String guardarAlquiler(@RequestParam String dniInquilino, @RequestParam Integer idInmueble,
            @RequestParam String fechaInicio, @RequestParam String fechaFin,
            @RequestParam Double precio, ModelMap modelo) {
        try {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            
            // Verificar que el inquilino existe
            inquilinoServicio.getOne(dniInquilino);
            
            // Aquí necesitaríamos el DNI del agente
            String dniAgente = "ADMIN"; // TODO: obtener el agente logueado
            alquilerServicio.crearAlquiler(inicio, fin, precio, idInmueble, 
                    dniAgente, dniInquilino, List.of());
                    
            modelo.put("exito", "El alquiler fue registrado correctamente!");
            return "redirect:/";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "alquiler/form";
        }
    }

    @GetMapping("/buscarInquilino")
    public String buscarInquilino(ModelMap modelo) {
        List<Inquilino> inquilinos = inquilinoServicio.listarInquilinos();
        modelo.addAttribute("inquilinos", inquilinos);
        return "alquiler/buscarInquilino";
    }

    @PostMapping("/buscarInquilino")
    public String seleccionarInquilino(@RequestParam String dniInquilino, ModelMap modelo) {
        try {
            // Validar que el inquilino existe
            inquilinoServicio.getOne(dniInquilino);
            List<Alquiler> alquileres = alquilerServicio.listarAlquileres();
            alquileres = alquileres.stream()
                                 .filter(a -> a.getMiInquilino() != null && a.getMiInquilino().getDniInquilino().equals(dniInquilino))
                                 .collect(Collectors.toList());
            modelo.addAttribute("alquileres", alquileres);
            return "alquiler/listar";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "alquiler/buscarInquilino";
        }
    }

    @GetMapping("/listar")
    public String listarAlquileres(ModelMap modelo) {
        List<Alquiler> alquileres = alquilerServicio.listarAlquileres();
        modelo.addAttribute("alquileres", alquileres);
        return "alquiler/listar";
    }
}