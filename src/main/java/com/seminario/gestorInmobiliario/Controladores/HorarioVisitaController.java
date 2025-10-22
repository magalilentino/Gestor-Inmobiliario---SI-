package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.seminario.gestorInmobiliario.Entidades.HorarioVisita;
import com.seminario.gestorInmobiliario.Repositorios.HorarioVisitaRepository;
import com.seminario.gestorInmobiliario.Repositorios.PropiedadRepository;
import com.seminario.gestorInmobiliario.Servicios.HorarioVisitaService; 

@Controller
@RequestMapping("/visitas")
public class HorarioVisitaController {

    @Autowired
    private PropiedadRepository propiedadRepo;

    @Autowired
    private HorarioVisitaRepository horarioRepo;
    
    // 2. INYECTA TU SERVICIO
    @Autowired
    private HorarioVisitaService horarioVisitaService;

    // ... (el método mostrarFormulario sigue igual) ...
    @GetMapping("/registrar-horarios")
    public String mostrarFormulario(Model model) {
        model.addAttribute("titulo", "Registrar Horarios de Visita");
        model.addAttribute("horario", new HorarioVisita());
        model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
        return "visitas/registrar-horarios";
    }

    // Procesar formulario
    @PostMapping("/registrar-horarios")
    public String registrarHorario(@ModelAttribute("horario") HorarioVisita horario, Model model) {

        // Puedes mover esta validación al servicio también, pero está bien aquí.
        if (horario.getHoraFin().isBefore(horario.getHoraIni()) || horario.getHoraFin().equals(horario.getHoraIni())) {
            model.addAttribute("error", "La hora de fin debe ser posterior a la hora de inicio.");
            model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
            return "visitas/registrar-horarios";
        }

        // Validar que no se solape
        List<HorarioVisita> solapados = horarioRepo.findSolapados(
                horario.getPropiedad().getIdPropiedad(),
                horario.getFecha(),
                horario.getHoraIni(),
                horario.getHoraFin()
        );

        if (!solapados.isEmpty()) {
            model.addAttribute("error", "El horario ingresado se solapa con otro existente para esta propiedad.");
            model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
            return "visitas/registrar-horarios";
        }

        // 3. USA EL SERVICIO PARA GUARDAR Y MANEJA LA EXCEPCIÓN
        try {
            horario.setDisponible(true);
            // Esta llamada AHORA SÍ valida la duración de 30 minutos
            horarioVisitaService.guardarHorario(horario); 
            
            model.addAttribute("exito", "Horario registrado correctamente.");
            model.addAttribute("horario", new HorarioVisita()); // Resetea el formulario

        } catch (IllegalArgumentException e) {
            // Captura el error de validación del servicio (ej. "La duración debe ser...")
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            // Captura cualquier otro error inesperado
            model.addAttribute("error", "Ocurrió un error inesperado al guardar el horario.");
        }

        model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
        return "visitas/registrar-horarios";
    }
}