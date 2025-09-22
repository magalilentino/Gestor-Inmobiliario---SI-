package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.FormaPago;
import com.seminario.gestorInmobiliario.Repositorios.FormaPagoRepository;

@Service
public class FormaPagoServicio {

    @Autowired
    private FormaPagoRepository formaPagoRepositorio;

    @Transactional
    public void crearFormaPago(String nombre) throws Exception {
        validar(nombre);

        FormaPago formaPago = new FormaPago();
        formaPago.setNombre(nombre);

        formaPagoRepositorio.save(formaPago);
    }

    @Transactional(readOnly = true)
    public List<FormaPago> listarFormasPago() {
        return formaPagoRepositorio.findAll();
    }

    @Transactional
    public void modificarFormaPago(Integer idFormaPago, String nombre) throws Exception {
        validar(nombre);

        FormaPago formaPago = formaPagoRepositorio.findById(idFormaPago)
                .orElseThrow(() -> new Exception("La forma de pago especificada no existe."));

        formaPago.setNombre(nombre);

        formaPagoRepositorio.save(formaPago);
    }

    @Transactional(readOnly = true)
    public FormaPago getOne(Integer idFormaPago) {
        return formaPagoRepositorio.getReferenceById(idFormaPago);
    }

    private void validar(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("El nombre no puede ser nulo o estar vacío.");
        }
    }
}
