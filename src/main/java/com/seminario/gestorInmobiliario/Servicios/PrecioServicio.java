package com.seminario.gestorInmobiliario.Servicios;

import java.time.LocalDate;
<<<<<<< Updated upstream
import java.util.ArrayList;
import java.util.Date;
=======
>>>>>>> Stashed changes
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< Updated upstream
import com.seminario.gestorInmobiliario.Entidades.Precio;
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Repositorios.PrecioRepository;
import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;
=======
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Precio;
import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;
import com.seminario.gestorInmobiliario.Repositorios.PrecioRepository;
>>>>>>> Stashed changes

@Service
public class PrecioServicio {

    @Autowired
    private PrecioRepository precioRepository;

    @Autowired
    private AlquilerRepository alquilerRepository;

<<<<<<< Updated upstream

    @Transactional 
    public void crearPrecio(LocalDate mesDesde, LocalDate anioDesde, double montoPrecio, Integer idAlquiler)
        throws Exception {
=======
    @Transactional
    public void crearPrecio(LocalDate mesDesde, LocalDate anioDesde, double montoPrecio, Integer idAlquiler)
            throws Exception {
>>>>>>> Stashed changes

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
<<<<<<< Updated upstream
        List<Precio> precios = new ArrayList<>();

        precios = precioRepository.findAll();
        return precios;
=======
        return precioRepository.findAll();
>>>>>>> Stashed changes
    }

    @Transactional
    public void modificarPrecio(Integer idPrecio, LocalDate mesDesde, LocalDate anioDesde, double montoPrecio, Integer idAlquiler)
<<<<<<< Updated upstream
        throws Exception {
=======
            throws Exception {
>>>>>>> Stashed changes

        validar(mesDesde, anioDesde, montoPrecio, idAlquiler);
        Optional<Precio> precioOpt = precioRepository.findById(idPrecio);
        Optional<Alquiler> alquilerOpt = alquilerRepository.findById(idAlquiler);

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        Alquiler alquiler = new Alquiler();

        if (alquilerOpt.isPresent()) {
            alquiler = alquilerOpt.get();
        }

<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    public void eliminarPrecio(Integer idPrecio) throws Exception{
=======
    public void eliminarPrecio(Integer idPrecio) throws Exception {
>>>>>>> Stashed changes
        Optional<Precio> precioOpt = precioRepository.findById(idPrecio);
        if (precioOpt.isPresent()) {
            precioRepository.delete(precioOpt.get());
        } else {
            throw new Exception("El precio con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
<<<<<<< Updated upstream
    public Precio  getOne(Integer idPrecio){
        return precioRepository.getReferenceById (idPrecio);
    }

    private void validar(LocalDate mesDesde, LocalDate anioDesde, double montoPrecio, Integer idAlquiler)
        throws Exception {
        
=======
    public Precio getOne(Integer idPrecio) {
        return precioRepository.getReferenceById(idPrecio);
    }

    private void validar(LocalDate mesDesde, LocalDate anioDesde, double montoPrecio, Integer idAlquiler)
            throws Exception {

>>>>>>> Stashed changes
        if (mesDesde == null) {  //no valide que la fecha sea mayor a la de hoy porque supongo que se puede cargar o modificar precios anteriores y a futuro 
            throw new Exception("El nombre no puede ser nulo o estar vacio");
        }

<<<<<<< Updated upstream
        if(idAlquiler == null) {
=======
        if (idAlquiler == null) {
>>>>>>> Stashed changes
            throw new Exception("El idAlquiler no puede ser nulo o estar vacio");
        }

        if (anioDesde == null) {
            throw new Exception("El codPostal no puede ser nulo o estar vacio");
        }

        if (montoPrecio == 0) {
            throw new Exception("El precio no puede ser nulo o estar vacio");
        }
    }
<<<<<<< Updated upstream
}
=======
}
>>>>>>> Stashed changes
