package com.seminario.gestorInmobiliario.Controladores;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // <-- IMPORTA ESTO
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

    // ... (tus @Autowired)
    @Autowired
    private PropiedadRepository propiedadRepo;
    @Autowired
    private HorarioVisitaRepository horarioRepo;
    @Autowired
    private HorarioVisitaService horarioVisitaService;

    @GetMapping("/registrar-horarios")
    public String mostrarFormulario(Model model) {
        // ... (tu método GET está bien)
        model.addAttribute("titulo", "Registrar Horarios de Visita");
        model.addAttribute("horario", new HorarioVisita());
        model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
        return "visitas/registrar-horarios";
    }

    // Procesar formulario
    @PostMapping("/registrar-horarios")
    public String registrarHorario(
            @ModelAttribute("horario") HorarioVisita horario,
            BindingResult result, // <-- AÑADE ESTO (Debe ir JUSTO después del @ModelAttribute)
            Model model) {

        // --- 1. VERIFICACIÓN DE BINDING (NUEVO) ---
        // Atrapa errores de conversión (ej. texto vacío a fecha)
        if (result.hasErrors()) {
            // Un error de conversión ocurrió ANTES de poder hacer nada.
            // Volvemos al formulario.
            model.addAttribute("error", "Error en los datos ingresados. Verifique que todos los campos estén completos.");
            model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
            return "visitas/registrar-horarios";
        }
        // --- FIN DE LA VERIFICACIÓN ---


        // --- 2. LÓGICA DE COMBINACIÓN (YA LA TENÍAS, PERO MEJORADA) ---
        try {
            LocalDate fecha = horario.getFecha();
            LocalTime horaInicio = horario.getHoraIniForm();
            LocalTime horaFinal = horario.getHoraFinForm();
            
            // Verificamos nulos (que el BindingResult pudo haber dejado pasar)
            if (fecha == null || horaInicio == null || horaFinal == null) {
                throw new IllegalArgumentException("La fecha, hora de inicio y hora de fin son obligatorias.");
            }

            LocalDateTime fechaHoraInicioCorrecta = LocalDateTime.of(fecha, horaInicio);
            LocalDateTime fechaHoraFinCorrecta = LocalDateTime.of(fecha, horaFinal);

            horario.setHoraIni(fechaHoraInicioCorrecta);
            horario.setHoraFin(fechaHoraFinCorrecta);

        // --- LÍNEA CORREGIDA ---
        } catch (NullPointerException | IllegalArgumentException | DateTimeException e) {
            model.addAttribute("error", "Error al procesar la fecha y hora: " + e.getMessage());
            model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
            return "visitas/registrar-horarios";
        }

        // --- 3. RESTO DE TUS VALIDACIONES ---
        // (Esto ya lo tenías y estaba bien)
        
        if (horario.getHoraFin().isBefore(horario.getHoraIni()) || horario.getHoraFin().equals(horario.getHoraIni())) {
            model.addAttribute("error", "La hora de fin debe ser posterior a la hora de inicio.");
            model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
            return "visitas/registrar-horarios";
        }

        List<HorarioVisita> solapados = horarioRepo.findSolapados(
                horario.getPropiedad().getIdPropiedad(),
                horario.getHoraIni(),
                horario.getHoraFin()
        );

        if (!solapados.isEmpty()) {
            model.addAttribute("error", "El horario ingresado se solapa con otro existente para esta propiedad.");
            model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
            return "visitas/registrar-horarios";
        }

        try {
            horario.setDisponible(true);
            horarioVisitaService.guardarHorario(horario);

            model.addAttribute("exito", "Horario registrado correctamente.");
            model.addAttribute("horario", new HorarioVisita());

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "Ocurrió un error inesperado al guardar el horario.");
        }

        model.addAttribute("propiedades", propiedadRepo.findByEstadoIgnoreCase("Disponible"));
        return "visitas/registrar-horarios";
    }
}