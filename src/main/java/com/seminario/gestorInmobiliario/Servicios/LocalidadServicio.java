package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Localidad;
import com.seminario.gestorInmobiliario.Repositorios.LocalidadRepository;

@Service
public class LocalidadServicio {
    
    @Autowired
    private LocalidadRepository localidadRepository;

    @Transactional(readOnly = true)
    public List<Localidad> listarLocalidades() {
        return localidadRepository.findAll();
    }

    @Transactional
    public void crearLocalidad(String nombre) throws Exception {
        validar(nombre);
        
        Localidad localidad = new Localidad();
        localidad.setNombre(nombre);
        
        localidadRepository.save(localidad);
    }

    @Transactional
    public void modificarLocalidad(Integer id, String nombre) throws Exception {
        validar(nombre);
        
        Localidad localidad = localidadRepository.findById(id)
                .orElseThrow(() -> new Exception("La localidad no existe."));
        
        localidad.setNombre(nombre);
        localidadRepository.save(localidad);
    }

    @Transactional
    public void eliminarLocalidad(Integer id) throws Exception {
        Localidad localidad = localidadRepository.findById(id)
                .orElseThrow(() -> new Exception("La localidad no existe."));
        
        localidadRepository.delete(localidad);
    }

    @Transactional(readOnly = true)
    public Localidad getOne(Integer id) throws Exception {
        return localidadRepository.findById(id)
                .orElseThrow(() -> new Exception("La localidad no existe."));
    }

    private void validar(String nombre) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("El nombre no puede estar vacío");
        }
    }

    @Transactional(readOnly = true)
    public List<Localidad> buscarPorProvincia(int idProv){
        return localidadRepository.buscarPorProvincia(idProv);
    }
}