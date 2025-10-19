package com.seminario.gestorInmobiliario.Controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Entidades.HorarioVisita;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Repositorios.HorarioVisitaRepository;
import com.seminario.gestorInmobiliario.Repositorios.PropiedadRepository;

@Controller
public class VisitaController {

    @Autowired
    private PropiedadRepository propiedadRepo;

    @Autowired
    private HorarioVisitaRepository horarioRepo;

    // 1️⃣ Página inicial de agendar visitas
    @GetMapping("/agendar-visitas")
    public String mostrarPagina() {
        return "agendar-visitas";
    }

    // 2️⃣ Buscar horarios disponibles de una o varias propiedades
    @GetMapping("/visitas/buscar")
    public String buscarVisitas(@RequestParam("query") String query, Model model) {

        List<Propiedad> propiedadesEncontradas = new ArrayList<>();
        List<HorarioVisita> horariosTotales = new ArrayList<>();

        try {
            // Buscar por ID si es un número
            int id = Integer.parseInt(query);
            Propiedad propiedad = propiedadRepo.findById(id).orElse(null);
            if (propiedad != null) {
                propiedadesEncontradas.add(propiedad);
            }
        } catch (NumberFormatException e) {
            // Buscar por ubicación si es texto
            propiedadesEncontradas = propiedadRepo.findByUbicacionContainingIgnoreCase(query);
        }

        if (propiedadesEncontradas.isEmpty()) {
            model.addAttribute("mensajeError", "No se encontró ninguna propiedad con el criterio '" + query + "'.");
        } else {
            for (Propiedad propiedad : propiedadesEncontradas) {
                List<HorarioVisita> horarios = horarioRepo.findByPropiedadAndDisponibleTrue(propiedad);
                horariosTotales.addAll(horarios);
            }

            if (horariosTotales.isEmpty()) {
                model.addAttribute("mensajeAdvertencia", "No hay horarios disponibles para las propiedades que coinciden con '" + query + "'.");
            } else {
                model.addAttribute("horarios", horariosTotales);
            }
        }

        return "/visitas/agendar-visitas";
    }

}
