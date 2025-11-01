package com.seminario.gestorInmobiliario.Controladores;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // <-- Ahora usa 'Visitas'
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.seminario.gestorInmobiliario.Entidades.HorarioVisita;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Entidades.Visitas;
import com.seminario.gestorInmobiliario.Repositorios.HorarioVisitaRepository;
import com.seminario.gestorInmobiliario.Repositorios.PropiedadRepository;
import com.seminario.gestorInmobiliario.Servicios.VisitasServicio;

@Controller
public class VisitaController {

    @Autowired
    private PropiedadRepository propiedadRepo;

    @Autowired
    private HorarioVisitaRepository horarioRepo;

    @Autowired
    private VisitasServicio visitasService;

    // 1. Página inicial
    @GetMapping("/agendar-visitas")
    public String mostrarPagina() {
        return "visitas/agendar-visitas";
    }

    // 2. Buscar horarios
    @GetMapping("/visitas/buscar")
    public String buscarVisitas(@RequestParam("query") String query, Model model) {
        // (Tu lógica de búsqueda original... está correcta y no la modifico)
        if (query == null || query.trim().isEmpty()) {
            List<HorarioVisita> horariosTotales = horarioRepo.findAllDisponiblesOrdenados();
            if (horariosTotales.isEmpty()) {
                model.addAttribute("mensajeAdvertencia", "No hay ningún horario disponible registrado en el sistema.");
            } else {
                model.addAttribute("horarios", horariosTotales);
            }
        } else {
            List<Propiedad> propiedadesEncontradas = new ArrayList<>();
            try {
                int id = Integer.parseInt(query);
                Propiedad propiedad = propiedadRepo.findById(id).orElse(null);
                if (propiedad != null) {
                    propiedadesEncontradas.add(propiedad);
                }
            } catch (NumberFormatException e) {
                propiedadesEncontradas = propiedadRepo.findByUbicacionContainingIgnoreCase(query);
            }
            if (propiedadesEncontradas.isEmpty()) {
                model.addAttribute("mensajeError", "No se encontró ninguna propiedad con el criterio '" + query + "'.");
            } else {
                List<HorarioVisita> horariosTotales = horarioRepo.findHorariosDisponiblesOrdenados(propiedadesEncontradas);
                if (horariosTotales.isEmpty()) {
                    model.addAttribute("mensajeAdvertencia", "No hay horarios disponibles para las propiedades que coinciden con '" + query + "'.");
                } else {
                    model.addAttribute("horarios", horariosTotales);
                }
            }
        }
        return "/visitas/agendar-visitas";
    }

    // 3. Mostrar formulario de visitante
    @GetMapping("/visitas/formulario-visitante")
    public String mostrarFormularioVisitante(@RequestParam("idSeleccionado") Integer idHorario, Model model, RedirectAttributes redirectAttributes) {

        HorarioVisita horarioSeleccionado = horarioRepo.findById(idHorario).orElse(null);

        if (horarioSeleccionado == null || !horarioSeleccionado.getDisponible()) { // Verifico si está disponible
            redirectAttributes.addFlashAttribute("mensajeError", "El horario seleccionado ya no está disponible.");
            return "redirect:/agendar-visitas";
        }

        model.addAttribute("horario", horarioSeleccionado);
        return "visitas/formulario-visitante";
    }

    // 4. Procesar la reserva final (AQUÍ ESTÁN LOS CAMBIOS IMPORTANTES)
    @PostMapping("/visitas/reservar")
    public String procesarReserva(
            @RequestParam("idHorario") Integer idHorario,
            @RequestParam("nombreVisitante") String nombre,
            @RequestParam("apellidoVisitante") String apellido,
            @RequestParam("telefonoVisitante") String telefono,
            @RequestParam("emailVisitante") String email, // <-- 2. AÑADIMOS EL EMAIL
            RedirectAttributes redirectAttributes) {

        try {
            HorarioVisita horario = horarioRepo.findById(idHorario).orElse(null);
            if (horario == null) {
                throw new Exception("El horario seleccionado ya no está disponible.");
            }
            if (!horario.getDisponible()) {
                throw new Exception("El horario acaba de ser reservado por otra persona.");
            }

            // --- 3. CAMBIOS EN LOS SETTERS ---
            // Usamos 'Visitas' (plural)
            Visitas nuevaVisita = new Visitas();

            // Usamos los setters de tu entidad
            nuevaVisita.setPropiedad(horario.getPropiedad()); // setPropiedad -> setIdPropiedad
            nuevaVisita.setFecha(horario.getFecha());
            nuevaVisita.setHoraIni(horario.getHoraIni());       // setHora -> setHoraIni
            nuevaVisita.setHoraFin(horario.getHoraFin());       // Añadido
            nuevaVisita.setNombre(nombre);                      // setNombreVisitante -> setNombre
            nuevaVisita.setApellido(apellido);                  // setApellidoVisitante -> setApellido
            nuevaVisita.setTelefono(telefono);                  // setTelefonoVisitante -> setTelefono
            nuevaVisita.setEmail(email);                        // Añadido

            // 3. Guardar la visita
            visitasService.guardarVisita(nuevaVisita);

            // 4. Marcar el horario como NO disponible
            horario.setDisponible(false);
            horarioRepo.save(horario);

            redirectAttributes.addFlashAttribute("mensajeExito", "¡Visita registrada con éxito!");
            return "redirect:/visitas/exito";   // <-- LÍNEA NUEVA

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al registrar la visita: " + e.getMessage());
            return "redirect:/agendar-visitas";
        }
    }

    @GetMapping("/visitas/exito")
    public String mostrarPaginaExito() {
        return "visitas/visita-registrada"; 
    }

}
