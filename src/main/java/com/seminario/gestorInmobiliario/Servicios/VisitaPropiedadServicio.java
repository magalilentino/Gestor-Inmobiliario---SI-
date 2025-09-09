package com.seminario.gestorInmobiliario.Servicios;

import com.seminario.gestorInmobiliario.Entidades.VisitaPropiedad;
import com.seminario.gestorInmobiliario.Repositorios.VisitaPropiedadRepository;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Repositorios.PropiedadRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VisitaPropiedadServicio {
    @Autowired
    private VisitaPropiedadRepository visitaPropiedadRepository;
    @Autowired
    private PropiedadRepository propiedadRepository;
    @Autowired
    private VisitaRepository visitaRepository;

    @Transactional
    public void crearVisitaPropiedad(int idVisita, int idPropiedad) throws Exception {
        validar (idVisita, idPropiedad);

        VisitaPropiedad visitaPropiedad = new VisitaPropiedad();
        Propiedad propiedad = propiedadRepository.findById(idPropiedad);
        if (propiedad == null) {
            throw new Exception("La propiedad indicada no existe");
        }
        Visita visita = visitaRepository.findById(idVisita);
        if (visita == null) {
            throw new Exception("La visita indicada no existe");
        }
        visitaPropiedad.setVisita(visita);
        visitaPropiedad.setPropiedad(propiedad);

        visitaPropiedadRepository.save(visitaPropiedad);
    }

    @Transactional
    public void eliminarVisitaPropiedad(int idVisitaProp) throws Exception {
        if (idVisitaProp <= 0) {
            throw new Exception("El id de la visita propiedad debe ser un valor positivo");
        }
        Optional<VisitaPropiedad> respuesta = visitaPropiedadRepository.findById(idVisitaProp);
        if (respuesta.isPresent()) {
            VisitaPropiedad visitaPropiedad = respuesta.get();
            visitaPropiedadRepository.delete(visitaPropiedad);
        } else {
            throw new Exception("No se encontro la visita propiedad solicitada");
        }
    }

    @Transactional
    public void actualizarVisitaPropiedad(int idVisitaProp, int idVisita, int idPropiedad) throws Exception {
        validar(idVisita, idPropiedad);
        Optional<VisitaPropiedad> respuesta = visitaPropiedadRepository.findById(idVisitaProp);
        if (respuesta.isPresent()) {
            VisitaPropiedad visitaPropiedad = respuesta.get();
            visitaPropiedad.setVisita(visitaRepository.findById(idVisita));
            visitaPropiedad.setPropiedad(propiedadRepository.findById(idPropiedad));
            visitaPropiedadRepository.save(visitaPropiedad);
        } else {
            throw new Exception("No se encontro la visita propiedad solicitada");
        }
    }

    @Transactional(readOnly = true)
    public List<VisitaPropiedad> listarVisitasPropiedades() {
        return visitaPropiedadRepository.findAll();
    }

    @Transactional(readOnly = true)
    public VisitaPropiedad getOne(int idVisitaProp) throws Exception {
        if (idVisitaProp <= 0) {
            throw new Exception("El id de la visita propiedad debe ser un valor positivo");
        }
        Optional<VisitaPropiedad> respuesta = visitaPropiedadRepository.findById(idVisitaProp);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("No se encontro la visita propiedad solicitada");
        }
    }

    private void validar(int idVisita, int idPropiedad) throws Exception {
        if (idVisita <= 0) {
            throw new Exception("El id de la visita debe ser un valor positivo");
        }
        if (idPropiedad <= 0) {
            throw new Exception("El id de la propiedad debe ser un valor positivo");
        }
    }

