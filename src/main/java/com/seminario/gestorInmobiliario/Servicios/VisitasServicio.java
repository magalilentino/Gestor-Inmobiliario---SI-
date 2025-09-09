package com.seminario.gestorInmobiliario.Servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Repositorios.VisitasRepository;
import com.seminario.gestorInmobiliario.Entidades.Visitas;


@Service
public class VisitasServicio {

    @Autowired
    private VisitasRepository visitasRepositorio;

    @Transactional
    public void crearVisitas(LocalDateTime fechaHora, String nombre, String telefono, String email) throws Exception {

        validar(fechaHora, nombre, telefono, email);

        Visitas visitas = new Visitas();

        visitas.setFechahora(fechaHora);
        visitas.setNombre(nombre);
        visitas.setTelefono(telefono);
        visitas.setEmail(email);

        visitasRepositorio.save(visitas);
    }

    @Transactional(readOnly = true)
    public List<Visitas> listarVisitas() {
        return visitasRepositorio.findAll();
    }

    @Transactional
    public void modificarVisitas(LocalDateTime fechaHora, String nombre, String telefono, String email, int nro) throws Exception {
        
         validar(fechaHora, nombre, telefono, email);

        Optional<Visitas> visitasOpt = visitasRepositorio.findById(nro);

        if (visitasOpt.isPresent()) {
            Visitas visitas = visitasOpt.get();

            visitas.setFechahora(fechaHora);
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