package com.seminario.gestorInmobiliario.Servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Precio;
import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;
import com.seminario.gestorInmobiliario.Repositorios.PrecioRepository;

@Service
public class PrecioServicio {

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Transactional 
    public void crearPrecio(LocalDate fechaDesde, double montoPrecio, Integer idAlquiler)
        throws Exception {

        validar(fechaDesde, montoPrecio, idAlquiler);

        Alquiler alquiler = alquilerRepository.findById(idAlquiler).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }

        Precio precio = new Precio();

        precio.setFechaDesde(fechaDesde);
        precio.setPrecio(montoPrecio);
        precio.setAlquiler(alquiler);

        precioRepository.save(precio);

    }

    @Transactional(readOnly = true)
    public List<Precio> listarPrecios() {
        return precioRepository.findAll();
    }

    @Transactional
    public void modificarPrecio(Integer idPrecio, LocalDate fechaDesde, double montoPrecio, Integer idAlquiler)
        throws Exception {

        validar(fechaDesde, montoPrecio, idAlquiler);
        Optional<Precio> precioOpt = precioRepository.findById(idPrecio);
        Optional<Alquiler> alquilerOpt = alquilerRepository.findById(idAlquiler);

        Alquiler alquiler = new Alquiler();

        if (alquilerOpt.isPresent()) {
            alquiler = alquilerOpt.get();
        }

        if (precioOpt.isPresent()) {
            Precio precio = precioOpt.get();

            precio.setFechaDesde(fechaDesde);
            precio.setPrecio(montoPrecio);
            precio.setAlquiler(alquiler);

            precioRepository.save(precio);
        }

    }

    @Transactional
    public void eliminarPrecio(Integer idPrecio) throws Exception {
        Optional<Precio> precioOpt = precioRepository.findById(idPrecio);
        if (precioOpt.isPresent()) {
            precioRepository.delete(precioOpt.get());
        } else {
            throw new Exception("El precio con el ID especificado no existe");
        }

    }


    @Transactional(readOnly = true)
    public Precio getOne(Integer idPrecio) {
        return precioRepository.getReferenceById(idPrecio);
    }

    private void validar(LocalDate fechaDesde, double montoPrecio, Integer idAlquiler)
        throws Exception {
        
        if (fechaDesde == null) {  //no valide que la fecha sea mayor a la de hoy porque supongo que se puede cargar o modificar precios anteriores y a futuro 
            throw new Exception("El nombre no puede ser nulo o estar vacio");
        }

        if (idAlquiler == null) {
            throw new Exception("El idAlquiler no puede ser nulo o estar vacio");
        }

        if (montoPrecio == 0) {
            throw new Exception("El precio no puede ser nulo o estar vacio");
        }
    }
}
