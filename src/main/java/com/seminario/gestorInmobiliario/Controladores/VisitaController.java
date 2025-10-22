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

        // --- INICIO DE LA MODIFICACIÓN ---

        // Comprueba si el 'query' es nulo o está vacío (después de quitar espacios)
        if (query == null || query.trim().isEmpty()) {
            
            // Caso 1: El usuario NO escribió nada. Buscar todos los disponibles.
            List<HorarioVisita> horariosTotales = horarioRepo.findAllDisponiblesOrdenados(); // <-- Se usa el nuevo método

            if (horariosTotales.isEmpty()) {
                model.addAttribute("mensajeAdvertencia", "No hay ningún horario disponible registrado en el sistema.");
            } else {
                model.addAttribute("horarios", horariosTotales);
            }

        } else {
            
            // Caso 2: El usuario SÍ escribió algo. Ejecutar la lógica de búsqueda que ya tenías.
            List<Propiedad> propiedadesEncontradas = new ArrayList<>();
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
                
                // Hacemos UNA SOLA CONSULTA a la BD con la lista de propiedades encontradas.
                List<HorarioVisita> horariosTotales = horarioRepo.findHorariosDisponiblesOrdenados(propiedadesEncontradas);

                if (horariosTotales.isEmpty()) {
                    model.addAttribute("mensajeAdvertencia", "No hay horarios disponibles para las propiedades que coinciden con '" + query + "'.");
                } else {
                    model.addAttribute("horarios", horariosTotales);
                }
            }
        }
        
        // --- FIN DE LA MODIFICACIÓN ---

        return "/visitas/agendar-visitas";
    }

}