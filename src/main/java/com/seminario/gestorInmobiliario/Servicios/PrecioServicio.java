package com.seminario.gestorInmobiliario.Servicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Precio;
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Repositorios.PrecioRepository;
import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;

@Service
public class PrecioServicio {

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;


    @Transactional 
    public void crearPrecio(LocalDate mesDesde, LocalDate anioDesde, double montoPrecio, Integer idAlquiler)
        throws Exception {

        validar(mesDesde, anioDesde, montoPrecio, idAlquiler);

        Alquiler alquiler = alquilerRepository.findById(idAlquiler).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }

        Precio precio = new Precio();

        precio.setMesDesde(mesDesde);
        precio.setAnioDesde(anioDesde);
        precio.setPrecio(montoPrecio);
        precio.setAlquiler(alquiler);

        precioRepository.save(precio);

    }

    @Transactional(readOnly = true)
    public List<Precio> listarPrecios() {
        List<Precio> precios = new ArrayList<>();

        precios = precioRepository.findAll();
        return precios;
    }

    @Transactional
    public void modificarPrecio(Integer idPrecio, LocalDate mesDesde, LocalDate anioDesde, double montoPrecio, Integer idAlquiler)
        throws Exception {

        validar(mesDesde, anioDesde, montoPrecio, idAlquiler);
        Optional<Precio> precioOpt = precioRepository.findById(idPrecio);
        Optional<Alquiler> alquilerOpt = alquilerRepository.findById(idAlquiler);


        Alquiler alquiler = new Alquiler();

        if (alquilerOpt.isPresent()) {
            alquiler = alquilerOpt.get();
        }


        if (precioOpt.isPresent()) {
            Precio precio = precioOpt.get();

            precio.setMesDesde(mesDesde);
            precio.setAnioDesde(anioDesde);
            precio.setPrecio(montoPrecio);
            precio.setAlquiler(alquiler);

            precioRepository.save(precio);
        }

    }

    @Transactional
    public void eliminarPrecio(Integer idPrecio) throws Exception{
        Optional<Precio> precioOpt = precioRepository.findById(idPrecio);
        if (precioOpt.isPresent()) {
            precioRepository.delete(precioOpt.get());
        } else {
            throw new Exception("El precio con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Precio  getOne(Integer idPrecio){
        return precioRepository.getReferenceById (idPrecio);
    }

    private void validar(LocalDate mesDesde, LocalDate anioDesde, double montoPrecio, Integer idAlquiler)
        throws Exception {
        
        if (mesDesde == null) {  //no valide que la fecha sea mayor a la de hoy porque supongo que se puede cargar o modificar precios anteriores y a futuro 
            throw new Exception("El nombre no puede ser nulo o estar vacio");
        }

        if(idAlquiler == null) {
            throw new Exception("El idAlquiler no puede ser nulo o estar vacio");
        }

        if (anioDesde == null) {
            throw new Exception("El codPostal no puede ser nulo o estar vacio");
        }

        if (montoPrecio == 0) {
            throw new Exception("El precio no puede ser nulo o estar vacio");
        }
    }
}