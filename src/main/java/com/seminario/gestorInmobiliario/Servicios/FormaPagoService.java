package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.gestorInmobiliario.Entidades.FormaPago;
import com.seminario.gestorInmobiliario.Repositorios.FormaPagoRepository;

@Service
public class FormaPagoService {

    @Autowired
    private FormaPagoRepository formaPagoRepository;

    // Crear o actualizar
    public FormaPago guardar(FormaPago formaPago) {
        return formaPagoRepository.save(formaPago);
    }

    // Listar todos
    public List<FormaPago> listarTodos() {
        return formaPagoRepository.findAll();
    }

    // Buscar por ID
    public Optional<FormaPago> buscarPorId(Long id) {
        return formaPagoRepository.findById(id);
    }

    // Eliminar por ID
    public void eliminar(Long id) {
        formaPagoRepository.deleteById(id);
    }
}
