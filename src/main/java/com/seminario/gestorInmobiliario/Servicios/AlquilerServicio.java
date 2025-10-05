package com.seminario.gestorInmobiliario.Servicios;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Aumento;
import com.seminario.gestorInmobiliario.Entidades.Documento;
import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import com.seminario.gestorInmobiliario.Entidades.Pago;
import com.seminario.gestorInmobiliario.Entidades.Precio;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Repositorios.AgenteRepository;
import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;
import com.seminario.gestorInmobiliario.Repositorios.AumentoRepository;
import com.seminario.gestorInmobiliario.Repositorios.DocumentoRepository;
import com.seminario.gestorInmobiliario.Repositorios.InquilinoRepository;
import com.seminario.gestorInmobiliario.Repositorios.PagoRepository;
import com.seminario.gestorInmobiliario.Repositorios.PropiedadRepository;


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

    @Autowired
    private AumentoRepository aumentoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private DocumentoRepository documentoRepositorio;

    @Autowired
    private PrecioServicio precioServicio;


    @Transactional 
    public void crearAlquiler(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial, int idPropiedad, String dniAgente, String dniInquilino, List<Documento> documentos)
            throws Exception {

        validar(fechaIngreso, fechaEgreso, valorInicial, idPropiedad, dniAgente, dniInquilino);

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
        alquiler.setDocumentos(documentos);

        alquilerRepositorio.save(alquiler);

    }

    @Transactional(readOnly = true)
    public List<Alquiler> listarAlquileres() {
        return alquilerRepositorio.findAll();
    }

    @Transactional
    public void modificarAlquiler(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial, int idPropiedad, String dniAgente, String dniInquilino, int id)
            throws Exception {
        
        validar(fechaIngreso, fechaEgreso, valorInicial, idPropiedad, dniAgente, dniInquilino);

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
        Aumento aumento = aumentoRepository.findById(idAumento).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }
        if (aumento == null) {
            throw new Exception("El aumento especificado no existe.");
        }

        alquiler.setMiAumento(aumento);

        alquilerRepositorio.save(alquiler);
    }

    public void quitarAumento(int id)throws Exception{
        Alquiler alquiler = alquilerRepositorio.findById(id).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }

        alquiler.setMiAumento(null);

        alquilerRepositorio.save(alquiler);
    }

    public double getPrecioActual(){
        List<Precio> misPrecios= precioServicio.listarPrecios();
        Precio precioActual;

        precioActual = misPrecios.get(0);

        
    for (Precio p : misPrecios) {
        // Primero comparamos el año
        if (p.getAñoDesde() > precioActual.getAñoDesde()) {
            precioActual = p;
        }
        // Si el año es igual, comparamos el mes
        else if (p.getAñoDesde() == precioActual.getAñoDesde() &&
                 p.getMesDesde() > precioActual.getMesDesde()) {
            precioActual = p;
        }
    }

    return precioActual.getPrecio();
}
        // for(Precio p: misPrecios){
        //     if (p.getFechaDesde().isAfter(precioActual.getFechaDesde())) {
        //     precioActual = p;}
        // }
        // return precioActual.getPrecio();


    public void agregarPago(int id, int idPago) throws Exception{
        Alquiler alquiler = alquilerRepositorio.findById(id).get();
        Pago pago = pagoRepository.findById(idPago).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }
        if (pago == null) {
            throw new Exception("El pago especificado no existe.");
        }

        alquiler.getMisPagos().add(pago);

        alquilerRepositorio.save(alquiler);
    }

    public void agregarDocumento(int id, int idDocumento) throws Exception{
        Alquiler alquiler = alquilerRepositorio.findById(id).get();
        Documento documento = documentoRepositorio.findById(idDocumento).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }
        if (documento == null) {
            throw new Exception("El documento especificado no existe.");
        }

        alquiler.getDocumentos().add(documento);

        alquilerRepositorio.save(alquiler);
    }

    public void resicionAlquiler(int id, LocalDate fechaResicion) throws Exception{
        Alquiler alquiler = alquilerRepositorio.findById(id).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }

        alquiler.setFechaRecision(fechaResicion);

        //hay que cambiar el estado de la propiedad a disponible

        alquilerRepositorio.save(alquiler);
    }

    private void validar(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial, int idPropiedad, String dniAgente, String dniInquilino)
            throws Exception {
        if (fechaIngreso == null) {
            throw new Exception("La fecha de ingreso no puede ser nula");
        }
        if (fechaEgreso == null) {
            throw new Exception("La fecha de egreso no puede ser nula");
        }
        if (valorInicial <= 0) {
            throw new Exception("El valor inicial debe ser un valor positivo");
        }
        if(idPropiedad == 0) {
            throw new Exception("El idPropiedad no puede ser nulo");
        }
        if (dniAgente.isEmpty() || dniAgente == null) {
            throw new Exception("El dni del agente no puede ser nulo o estar vacio");
        }
        if (dniInquilino.isEmpty() || dniInquilino == null) {
            throw new Exception("El dni del inquilino no puede ser nulo o estar vacio");
        }
    }

}