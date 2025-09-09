package com.seminario.gestorInmobiliario.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Repositorios.AumentoRepository;
import com.seminario.gestorInmobiliario.Entidades.Aumento;


@Service
public class AumentoServicio {

    @Autowired
    private AumentoRepository aumentoRepositorio;

    @Transactional
    public void crearAumento(int periodo, double porcentajeAumento ) throws Exception {

        //validar(nombre);

        Aumento aumento = new Aumento();

        aumento.setPeriodo(periodo);
        aumento.setPorcentajeAumento(porcentajeAumento);

        aumentoRepositorio.save(aumento);
    }

    @Transactional(readOnly = true)
    public List<Aumento> listarAumentos() {
        return aumentoRepositorio.findAll();
    }

    @Transactional
    public void modificarAumento(int periodo, double porcentajeAumento, int id) throws Exception {
        
        // validar(nombre);

        Optional<Aumento> aumentoOpt = aumentoRepositorio.findById(id);

        if (aumentoOpt.isPresent()) {
            Aumento aumento = aumentoOpt.get();

            aumento.setPeriodo(periodo);
            aumento.setPorcentajeAumento(porcentajeAumento);

            aumentoRepositorio.save(aumento);
        } else {
            throw new Exception("No se encontró un aumento con el ID especificado");
        }
    }

    @Transactional
    public void eliminarAumento(int id) throws Exception{
        Optional<Aumento> aumentoOpt = aumentoRepositorio.findById(id);
        if (aumentoOpt.isPresent()) {
            aumentoRepositorio.delete(aumentoOpt.get());
        } else {
            throw new Exception("El aumento con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Aumento getOne(int id){
        return aumentoRepositorio.getReferenceById(id);
    }


    // private void validar(String nombre) throws Exception {
    //     if (nombre.isEmpty() || nombre == null) {
    //         throw new Exception("el nombre no puede ser nulo o estar vacío");
    //     }
    // }

}
