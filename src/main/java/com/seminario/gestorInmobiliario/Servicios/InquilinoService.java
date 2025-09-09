package com.seminario.gestorInmobiliario.Servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import com.seminario.gestorInmobiliario.Repositorios.InquilinoRepository;
import com.seminario.gestorInmobiliario.Repositorios.InquilinoRepository;

@Service
public class InquilinoService {

    @Autowired
    private InquilinoRepository inquilinoRepository;

    @Transactional // Todos los metodos que generen cambios en la base de dados
    public void crearInquilino(String dniInquilino, String nomApe, String email, String telefono)
            throws Exception {

        validar(dniInquilino, email);
        Inquilino inquilino = new Inquilino();

        inquilino.setDniInquilino(dniInquilino); 
        inquilino.setNomApe(nomApe);
        inquilino.setEmail(email);
        inquilino.setTelefono(telefono);

        inquilinoRepository.save(inquilino);
    }

    @Transactional(readOnly = true)
    public List<Inquilino> listarInquilinos() {

        List<Inquilino> inquilinos = new ArrayList<>();

        inquilinos = inquilinoRepository.findAll();
        return inquilinos;
    }

    @Transactional
    public void modificarInquilino(String dniInquilino, String nomApe, String email, String telefono)
            throws Exception {

        validar(dniInquilino, email);
        Optional<Inquilino> respuesta = inquilinoRepository.findById(dniInquilino);
        if (respuesta.isPresent()) {
            Inquilino inquilino = respuesta.get();
            inquilino.setDniInquilino(dniInquilino);
            inquilino.setNomApe(nomApe);
            inquilino.setEmail(email);
            inquilino.setTelefono(telefono);
            
            inquilinoRepository.save(inquilino);
        } else {
            throw new Exception("No se encontro el inquilino solicitado");
        }
    }

    @Transactional
    public void eliminarInquilino(String dniInquilino) throws Exception {
        Optional<Inquilino> respuesta = inquilinoRepository.findById(dniInquilino);
        if (respuesta.isPresent()) {
            Inquilino inquilino = respuesta.get();
            inquilinoRepository.delete(inquilino);
        } else {
            throw new Exception("No se encontro el inquilino solicitado");
        }

    }

    @Transactional(readOnly = true)
    public Inquilino getOne(String dniInquilino) {
        return inquilinoRepository.getReferenceById (dniInquilino);
    }

    private void validar(String dniInquilino, String email) throws Exception {

        if (dniInquilino == null || dniInquilino.isEmpty()) {
            throw new Exception("El DNI del inquilino no puede ser nulo o estar vacio");
        }

        if (email == null || email.isEmpty()) {
            throw new Exception("El email del inquilino no puede ser nulo o estar vacio");
        }

    }
}