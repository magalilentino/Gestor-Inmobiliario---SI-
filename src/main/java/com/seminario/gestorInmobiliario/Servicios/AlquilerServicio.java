package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;
import com.seminario.gestorInmobiliario.Repositorios.PropiedadRepository;
import com.seminario.gestorInmobiliario.Repositorios.AgenteRepository;
import com.seminario.gestorInmobiliario.Repositorios.InquilinoRepository;
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Entidades.Inquilino;


@Service
public class AlquilerServicio {

    @Autowired
    private AlquilerRepository alquilerRepositorio;

    @Autowired
    private PropiedadRepository propiedadRepositorio;

    @Autowired
    private AgenteRepository agenteRepositorio;

    @Autowired
    private InquilinoRepository inquilinoRepositorio;

    @Transactional 
    public void crearAlquiler(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial, int idPropiedad, String dniAgente, String dniInquilino)
            throws Exception {
        // falta aumento, pagos, documentos y el atributo fechaRecision --> van en metodos aparte

        // validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Propiedad propiedad = propiedadRepositorio.findById(idPropiedad).get();
        Agente agente = agenteRepositorio.findById(dniAgente).get();
        Inquilino inquilino = inquilinoRepositorio.findById(dniInquilino).get();

        if (propiedad == null) {
            throw new Exception("La propiedad especificada no existe.");
        }
        if (agente == null) {
            throw new Exception("El agente especificado no existe.");
        }
        if (inquilino == null) {
            throw new Exception("El inquilino especificado no existe.");
        }

        Alquiler alquiler = new Alquiler();

        alquiler.setFechaIngreso(fechaIngreso);
        alquiler.setFechaEgreso(fechaEgreso);
        alquiler.setValorInicial(valorInicial);
        alquiler.setMiPropiedad(propiedad);
        alquiler.setMiAgente(agente);
        alquiler.setMiInquilino(inquilino);

        alquilerRepositorio.save(alquiler);

    }

    @Transactional(readOnly = true)
    public List<Alquiler> listarAlquileres() {
        return alquilerRepositorio.findAll();
    }

    @Transactional
    public void modificarAlquiler(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial, int idPropiedad, String dniAgente, String dniInquilino, int id)
            throws Exception {
        
        // validar(isbn, titulo, ejemplares, idAutor, idEditorial);

        Optional<Alquiler> alquilerOpt = alquilerRepositorio.findById(id);
        Optional<Propiedad> propiedadOpt = propiedadRepositorio.findById(idPropiedad);
        Optional<Agente> agenteOpt = agenteRepositorio.findById(dniAgente);
        Optional<Inquilino> inquilinoOpt = inquilinoRepositorio.findById(dniInquilino);

        if (alquilerOpt.isEmpty()) {
            throw new Exception("El alquiler especificado no existe.");
        }
        if (propiedadOpt.isEmpty()) {
            throw new Exception("La propiedad especificada no existe.");
        }
        if (agenteOpt.isEmpty()) {
            throw new Exception("El agente especificado no existe.");
        }
        if (inquilinoOpt.isEmpty()) {
            throw new Exception("El inquilino especificado no existe.");
        }

        Alquiler alquiler = alquilerOpt.get();
        alquiler.setFechaIngreso(fechaIngreso);
        alquiler.setFechaEgreso(fechaEgreso);
        alquiler.setValorInicial(valorInicial);
        alquiler.setMiPropiedad(propiedadOpt.get());
        alquiler.setMiAgente(agenteOpt.get());
        alquiler.setMiInquilino(inquilinoOpt.get());

        alquilerRepositorio.save(alquiler);

    }

    @Transactional(readOnly = true)
    public Alquiler getOne(int id){
        return alquilerRepositorio.getReferenceById(id);
    }

    public void agregarAumento(int id, int idAumento)throws Exception{
        Alquiler alquiler = alquilerRepositorio.findById(id).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }
    }

    // private void validar(Long isbn, String titulo, Integer ejemplares, UUID idAutor, UUID idEditorial)
    //         throws Exception {
    //     if (isbn == null) {
    //         throw new Exception("El isbn no puede ser nulo");
    //     }

    //     if (titulo.isEmpty() || titulo == null) {
    //         throw new Exception("El titulo no puede ser nulo o estar vacio");
    //     }

    //     if (ejemplares == null) {
    //         throw new Exception("ejemplares no puede ser nulo");
    //     }

    //     if(idAutor == null) {
    //         throw new Exception("El idAutor no puede ser nulo o estar vacio");
    //     }

    //     if (idEditorial == null) {
    //         throw new Exception("El idEditorial no puede ser nulo o estar vacio");
    //     }
    // }
}