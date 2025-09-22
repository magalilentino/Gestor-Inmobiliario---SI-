package com.seminario.gestorInmobiliario.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Localidad;
import com.seminario.gestorInmobiliario.Entidades.Provincia;
import com.seminario.gestorInmobiliario.Repositorios.LocalidadRepository;
import com.seminario.gestorInmobiliario.Repositorios.ProvinciaRepository;


@Service
public class LocalidadService {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;


    @Transactional // Todos los metodos que generen cambios en la base de dados
    public void crearLocalidad(String nombre, Integer codPostal, Integer idProvincia)
            throws Exception {

        validar(nombre,codPostal,idProvincia);

        Provincia provincia = provinciaRepository.findById(idProvincia).get();

        if (provincia == null) {
            throw new Exception("La provincia especificado no existe.");
        }

        Localidad localidad = new Localidad();

        localidad.setNombre(nombre);
        localidad.setCodPostal(codPostal);
        localidad.setMiProvincia(provincia);

        localidadRepository.save(localidad);

    }

    @Transactional(readOnly = true)
    public List<Localidad> listarLocalidades() {

        List<Localidad> localidades = new ArrayList<>();

        localidades = localidadRepository.findAll();
        return localidades;
    }

    @Transactional
    public void modificarLocalidad(Integer idLocalidad, String nombre, Integer codPostal, Integer idProvincia)
        throws Exception {

        validar(nombre,codPostal,idProvincia);
        Optional<Localidad> localidadOpt = localidadRepository.findById(idLocalidad);
        Optional<Provincia> provinciaOpt = provinciaRepository.findById(idProvincia);


        Provincia provincia = new Provincia();

        if (provinciaOpt.isPresent()) {
            provincia = provinciaOpt.get();
        }


        if (localidadOpt.isPresent()) {
            Localidad localidad = localidadOpt.get();

            localidad.setNombre(nombre);
            localidad.setCodPostal(codPostal);
            localidad.setMiProvincia(provincia);

            localidadRepository.save(localidad);
        }

    }

    @Transactional
    public void eliminarLocalidad(Integer idLocalidad) throws Exception{
        Optional<Localidad> localidadOpt = localidadRepository.findById(idLocalidad);
        if (localidadOpt.isPresent()) {
            localidadRepository.delete(localidadOpt.get());
        } else {
            throw new Exception("La localidad con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Localidad  getOne(Integer idLocalidad){
        return localidadRepository.getReferenceById (idLocalidad);
    }

    private void validar(String nombre, Integer codPostal, Integer idProvincia)
            throws Exception {
        
        if (nombre.isEmpty() || nombre == null) {
            throw new Exception("El nombre no puede ser nulo o estar vacio");
        }

        if(idProvincia == null) {
            throw new Exception("El idProvincia no puede ser nulo o estar vacio");
        }

        if (codPostal == null) {
            throw new Exception("El codPostal no puede ser nulo o estar vacio");
        }
    }
}