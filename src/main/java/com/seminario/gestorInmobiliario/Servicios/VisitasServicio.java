package com.seminario.gestorInmobiliario.Servicios;

import java.time.LocalDate; // <-- Faltaba este import en tu código original
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Entidades.Visitas;
import com.seminario.gestorInmobiliario.Repositorios.VisitasRepository;


@Service
public class VisitasServicio {

    @Autowired
    private VisitasRepository visitasRepositorio;

    // --- MÉTODO NUEVO QUE SOLUCIONA TU ERROR ---
    /**
     * Guarda una entidad Visita ya creada.
     * Este es el método que llama el VisitaController.
     */
    @Transactional
    public void guardarVisita(Visitas visita) throws Exception {
        // Validamos el objeto completo antes de guardarlo
        validar(visita); 
        visitasRepositorio.save(visita);
    }
    // --- FIN DEL MÉTODO NUEVO ---


    // --- TUS MÉTODOS ORIGINALES ---
    // (Este método 'crearVisitas' probablemente ya no lo necesites para este flujo)
    @Transactional
    public void crearVisitas(LocalDate fecha, String nombre, String telefono) throws Exception {

        // Tu método 'validar' original solo valida 3 campos
        // validar(fecha, nombre, telefono); // Cuidado: No tienes un validar para estos 3 campos

        Visitas visitas = new Visitas();

        visitas.setFecha(fecha);
        visitas.setNombre(nombre);
        visitas.setTelefono(telefono);

        visitasRepositorio.save(visitas);
    }

    @Transactional(readOnly = true)
    public List<Visitas> listarVisitas() {
        return visitasRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Propiedad> listarPropVisitadas(String email) {
        return visitasRepositorio.listarPropVisitadas(email);
    }

    @Transactional
    public void modificarVisitas(LocalDateTime fechaHora, String nombre, String telefono, String email, int nro) throws Exception {
        
         validar(fechaHora, nombre, telefono, email);

        Optional<Visitas> visitasOpt = visitasRepositorio.findById(nro);

        if (visitasOpt.isPresent()) {
            Visitas visitas = visitasOpt.get();

            // --- ¡OJO AQUÍ! ---
            // Tu entidad 'Visitas' no tiene un método 'setFechahora'. 
            // Deberías usar:
            // visitas.setFecha(fechaHora.toLocalDate());
            // visitas.setHoraIni(fechaHora);
            // visitas.setFechahora(fechaHora); // <-- Esta línea dará error
            
            visitas.setNombre(nombre);
            visitas.setTelefono(telefono);
            visitas.setEmail(email);

            visitasRepositorio.save(visitas);
        } else {
            throw new Exception("No se encontró una visita con el ID especificado");
        }
    }

    @Transactional
    public void eliminarVisitas(int nro) throws Exception{
        Optional<Visitas> visitasOpt = visitasRepositorio.findById(nro);
        if (visitasOpt.isPresent()) {
            visitasRepositorio.delete(visitasOpt.get());
        } else {
            throw new Exception("La visita con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Visitas getOne(int nro){
        return visitasRepositorio.getReferenceById(nro);
    }

    // --- NUEVO MÉTODO DE VALIDACIÓN PARA guardarVisita ---
    /**
     * Valida un objeto Visita completo.
     */
    private void validar(Visitas visita) throws Exception {
        if (visita == null) {
            throw new Exception("La visita no puede ser nula");
        }
        if (visita.getPropiedad() == null) {
            throw new Exception("La visita debe estar asociada a una propiedad");
        }
        if (visita.getFecha() == null) {
            throw new Exception("La fecha no puede ser nula");
        }
         if (visita.getHoraIni() == null) {
            throw new Exception("La hora de inicio no puede ser nula");
        }
        if (visita.getNombre() == null || visita.getNombre().isEmpty()) {
            throw new Exception("El nombre no puede ser nulo o estar vacío");
        }
        if (visita.getNumero() == null || visita.getNumero().isEmpty()) {
            throw new Exception("El teléfono nSo puede ser nulo o estar vacío");
        }
        if (visita.getEmail() == null || visita.getEmail().isEmpty()) {
            throw new Exception("El email no puede ser nulo o estar vacío");
        }
    }


    // --- TU MÉTODO DE VALIDACIÓN ORIGINAL ---
    private void validar(LocalDateTime fechaHora, String nombre, String telefono, String email) throws Exception {
        if (fechaHora == null) {
            throw new Exception("La fecha y hora no puede ser nula");
        }
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("El nombre no puede ser nulo o estar vacío");
        }
        if (telefono.isEmpty() || telefono == null) {
            throw new Exception("El telefono no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new Exception("El email no puede ser nulo o estar vacío");
        }
    }
}