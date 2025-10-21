package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Repositorios.ProvinciaRepository;
import com.seminario.gestorInmobiliario.Entidades.Provincia;


@Service
public class ProvinciaServicio {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional
    public void crearProvincia(String nombre) throws Exception {

        validar(nombre);

        Provincia provincia = new Provincia();

        provincia.setNombre(nombre);

        provinciaRepository.save(provincia);
    }

    @Transactional(readOnly = true)
    public List<Provincia> listarProvincias() {
        return provinciaRepository.findAll();
    }

    @Transactional
    public void modificarprovincias(String nombre, int idProvincia) throws Exception {
        
        validar(nombre);

        Optional<Provincia> provinciaOpt = provinciaRepository.findById(idProvincia);

        if (provinciaOpt.isPresent()) {
            Provincia provincia = provinciaOpt.get();

            provincia.setNombre(nombre);

            provinciaRepository.save(provincia);
        } else {
            throw new Exception("No se encontró una provincia con el ID especificado");
        }
    }

    @Transactional
    public void eliminarProvincia(int idProvincia) throws Exception{
        Optional<Provincia> provinciaOpt = provinciaRepository.findById(idProvincia);
        if (provinciaOpt.isPresent()) {
            provinciaRepository.delete(provinciaOpt.get());
        } else {
            throw new Exception("La provincia con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Provincia getOne(int idProvincia){
        return provinciaRepository.getReferenceById(idProvincia);
    }

    private void validar(String nombre) throws Exception {
        if (nombre == null || nombre.isEmpty()) {
            throw new Exception("el nombre no puede ser nulo o estar vacío");
        }
    }
}