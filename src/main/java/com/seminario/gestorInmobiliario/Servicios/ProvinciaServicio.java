package com.seminario.gestorInmobiliario.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Repositorios.ProvinciaRepository;
import com.seminario.gestorInmobiliario.Repositorios.ProvinciaRepository;
import com.seminario.gestorInmobiliario.Entidades.Provincia;


@Service
public class ProvinciaServicio {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional
    public void crearProvincia(String nombre) throws Exception {

        //validar(nombre);

        Provincia provincia = new Provincia();

        provincia.setNombre(nombre);

        provinciaRepository.save(provincia);
    }

    @Transactional(readOnly = true)
    public List<Provincia> listarProvincias() {
        return provinciaRepository.findAll();
    }

    @Transactional
    public void modificarprovincias(String nombre, int id) throws Exception {
        
        // validar(nombre);

        Optional<Provincia> provinciaOpt = provinciaRepository.findById(id);

        if (provinciaOpt.isPresent()) {
            Provincia provincia = provinciaOpt.get();

            provincia.setNombre(nombre);

            provinciaRepository.save(provincia);
        } else {
            throw new Exception("No se encontró una provincia con el ID especificado");
        }
    }

    @Transactional
    public void eliminarProvincia(int id) throws Exception{
        Optional<Provincia> provinciaOpt = provinciaRepository.findById(id);
        if (provinciaOpt.isPresent()) {
            provinciaRepository.delete(provinciaOpt.get());
        } else {
            throw new Exception("La provincia con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Provincia getOne(int id){
        return provinciaRepository.getReferenceById(id);
    }
}