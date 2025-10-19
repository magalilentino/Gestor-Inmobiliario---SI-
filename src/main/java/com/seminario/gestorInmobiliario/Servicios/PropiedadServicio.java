package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Categoria;
import com.seminario.gestorInmobiliario.Entidades.Localidad;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Repositorios.CategoriaRepository;
import com.seminario.gestorInmobiliario.Repositorios.LocalidadRepository;
import com.seminario.gestorInmobiliario.Repositorios.PropiedadRepository;

@Service
public class PropiedadServicio {

    @Autowired
    private PropiedadRepository propiedadRepository;
    @Autowired
    private LocalidadRepository localidadRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public void crearPropiedad(String ubicacion, String medidas, int cantAmbientes, int idLocalidad, int idCategoria, byte[] contenido) throws Exception {
        validar(ubicacion, medidas, cantAmbientes, idLocalidad, idCategoria);

        Localidad localidad = localidadRepository.findById(idLocalidad).get();
        if (localidad == null) {
            throw new Exception("La localidad indicada no existe");
        }
        Categoria categoria = categoriaRepository.findById(idCategoria).get();
        if (categoria == null) {
            throw new Exception("La categoria indicada no existe");
        }

        Propiedad propiedad = new Propiedad();

        propiedad.setUbicacion(ubicacion);
        propiedad.setMedidas(medidas);
        propiedad.setCantAmbientes(cantAmbientes);
        propiedad.setLocalidad(localidad);
        propiedad.setCategoria(categoria);
        propiedad.setContenido(contenido);
        propiedad.setEstado("Disponible");

        propiedadRepository.save(propiedad);
    }

    @Transactional
    public void modificarPropiedad(int idPropiedad, String ubicacion, String medidas, int cantAmbientes, int idlocalidad, int idcategoria, byte[] contenido) throws Exception {
        validar(ubicacion, medidas, cantAmbientes, idlocalidad, idcategoria);

        Optional<Propiedad> respuesta = propiedadRepository.findById(idPropiedad);
        if (respuesta.isPresent()) {
            Propiedad propiedad = respuesta.get();

            Localidad localidad = localidadRepository.findById(idlocalidad).get();
            if (localidad == null) {
                throw new Exception("La localidad indicada no existe");
            }
            Categoria categoria = categoriaRepository.findById(idcategoria).get();
            if (categoria == null) {
                throw new Exception("La categoria indicada no existe");
            }

            //propiedad.setId(idPropiedad);
            propiedad.setUbicacion(ubicacion);
            propiedad.setMedidas(medidas);
            propiedad.setCantAmbientes(cantAmbientes);
            propiedad.setLocalidad(localidad);
            propiedad.setCategoria(categoria);
            propiedad.setContenido(contenido);
            propiedadRepository.save(propiedad);
        } else {
            throw new Exception("No se encontro la propiedad solicitada");
        }

    }

    public List<Propiedad> listarPropiedades() {
        return propiedadRepository.findAll();
    }

    @Transactional
    public void eliminarPropiedad(int idPropiedad) throws Exception {
        Optional<Propiedad> respuesta = propiedadRepository.findById(idPropiedad);
        if (respuesta.isPresent()) {
            Propiedad propiedad = respuesta.get();
            propiedadRepository.delete(propiedad);
        } else {
            throw new Exception("No se encontro la propiedad solicitada");
        }
    }

    public Propiedad getOne(int idPropiedad) {
        return propiedadRepository.getReferenceById(idPropiedad);
    }

    public void cambiarEstadoPropiedad(int idPropiedad, String estado) throws Exception {
        Optional<Propiedad> respuesta = propiedadRepository.findById(idPropiedad);
        if (respuesta.isPresent()) {
            Propiedad propiedad = respuesta.get();
            propiedad.setEstado(estado);
            propiedadRepository.save(propiedad);
        } else {
            throw new Exception("No se encontro la propiedad solicitada");
        }
    }

    public List<Propiedad> buscarPorUbicacion(String ubicacion) {
        return propiedadRepository.findByUbicacionContainingIgnoreCase(ubicacion);
    }

    private void validar(String ubicacion, String medidas, Integer cantAmbientes, int idlocalidad, int idcategoria)
            throws Exception {

        if (ubicacion.isEmpty() || ubicacion == null) {
            throw new Exception("La ubicacion no puede ser nulo o estar vacio");
        }

        if (medidas.isEmpty() || medidas == null) {
            throw new Exception("Las medidas no puede ser nulo o estar vacio");
        }

        if (cantAmbientes == null) {
            throw new Exception("La cantidad de Ambientes no puede ser nulo o estar vacio");
        }

        if (idlocalidad == 0) {
            throw new Exception("La localidad no puede ser nula o estar vacio");
        }

        if (idcategoria == 0) {
            throw new Exception("La categoria no puede ser nulo o estar vacio");
        }
    }
}
