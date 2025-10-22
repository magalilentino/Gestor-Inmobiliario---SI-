package com.seminario.gestorInmobiliario.Servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
//import com.seminario.gestorInmobiliario.Entidades.Aumento;
// import com.seminario.gestorInmobiliario.Entidades.Documento;
import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import com.seminario.gestorInmobiliario.Entidades.Pago;
import com.seminario.gestorInmobiliario.Entidades.Precio;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Repositorios.AgenteRepository;
import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;
//import com.seminario.gestorInmobiliario.Repositorios.AumentoRepository;
// import com.seminario.gestorInmobiliario.Repositorios.DocumentoRepository;
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

    // @Autowired
    // private AumentoRepository aumentoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    // @Autowired
    // private DocumentoRepository documentoRepositorio;

    @Autowired
    private PrecioServicio precioServicio;


    @Transactional 
    public Alquiler crearAlquiler(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial, int idPropiedad, 
                            String dniAgente, String dniInquilino, int periodoAumento, 
                            double porcentajeAumento) throws Exception {

        validar(fechaIngreso, fechaEgreso, valorInicial, idPropiedad, dniAgente, dniInquilino, periodoAumento, porcentajeAumento);

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
        alquiler.setEstado("Activo");
        alquiler.setPeriodoAumento(periodoAumento);
        alquiler.setPorcentajeAumento(porcentajeAumento);

        alquilerRepositorio.save(alquiler);

        return alquiler;

    }

    @Transactional(readOnly = true)
    public List<Alquiler> listarAlquileres() {
        return alquilerRepositorio.findAll();
    }

    @Transactional
    public void modificarAlquiler(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial,
                                 int idPropiedad, String dniAgente, String dniInquilino, int id, 
                                 int periodoAumento, double porcentajeAumento) throws Exception {
        
        validar(fechaIngreso, fechaEgreso, valorInicial, idPropiedad, dniAgente, dniInquilino, periodoAumento, porcentajeAumento);

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
        alquiler.setPeriodoAumento(periodoAumento);
        alquiler.setPorcentajeAumento(porcentajeAumento);

        alquilerRepositorio.save(alquiler);

    }

    @Transactional(readOnly = true)
    public Alquiler getOne(int id){
        return alquilerRepositorio.getReferenceById(id);
    }
    
    /**
     * Obtiene un alquiler con todas sus relaciones cargadas completamente para evitar
     * problemas de LazyInitializationException al usar los datos en vistas
     * Cuando se pasa el alquiler a la vista (Thymeleaf), si no se cargan las relaciones lazy (inquilino, propiedad, documentos), se produce este error cuando la vista intenta acceder a ellas.
     * 
     * @param id ID del alquiler
     * @return Alquiler con todas sus relaciones inicializadas
     * @throws Exception Si no se encuentra el alquiler
     */
    @Transactional(readOnly = true)
    public Alquiler getAlquilerConDetallesCompletos(int id) throws Exception {
        Alquiler alquiler = alquilerRepositorio.findById(id)
                .orElseThrow(() -> new Exception("El alquiler con ID " + id + " no existe"));
        
        // Inicializar manualmente todas las relaciones necesarias
        if (alquiler.getMiInquilino() != null) {
            // Acceder a propiedades para forzar inicialización
            alquiler.getMiInquilino().getNomApe();
            alquiler.getMiInquilino().getDniInquilino();
        }
        
        if (alquiler.getMiPropiedad() != null) {
            // Acceder a propiedades para forzar inicialización
            alquiler.getMiPropiedad().getUbicacion();
            alquiler.getMiPropiedad().getEstado();
        }
        
        if (alquiler.getMiAgente() != null) {
            // Acceder a propiedades para forzar inicialización
            alquiler.getMiAgente().getNomApe();
        }
        
        // Inicializar documentos si existen
        // if (alquiler.getDocumentos() != null) {
        //     alquiler.getDocumentos().size(); // Esto fuerza la inicialización de la colección
        //     // También inicializar cada documento
        //     for (Documento doc : alquiler.getDocumentos()) {
        //         doc.getDescripcion(); // Esto fuerza la inicialización de cada documento
        //     }
        // }
        
        return alquiler;
    }

    // public void agregarAumento(int id, int idAumento)throws Exception{
    //     Alquiler alquiler = alquilerRepositorio.findById(id).get();
    //     Aumento aumento = aumentoRepository.findById(idAumento).get();

    //     if (alquiler == null) {
    //         throw new Exception("El alquiler especificado no existe.");
    //     }
    //     if (aumento == null) {
    //         throw new Exception("El aumento especificado no existe.");
    //     }

    //     alquiler.setMiAumento(aumento);

    //     alquilerRepositorio.save(alquiler);
    // }

    // public void quitarAumento(int id)throws Exception{
    //     Alquiler alquiler = alquilerRepositorio.findById(id).get();

    //     if (alquiler == null) {
    //         throw new Exception("El alquiler especificado no existe.");
    //     }

    //     alquiler.setMiAumento(null);

    //     alquilerRepositorio.save(alquiler);
    // }

    public double getPrecioActual(){
        List<Precio> misPrecios= precioServicio.listarPrecios();
        Precio precioActual;

        precioActual = misPrecios.get(0);
        for(Precio p: misPrecios){
            if (p.getFechaDesde().isAfter(precioActual.getFechaDesde())) {
            precioActual = p;}
        }
        return precioActual.getPrecio();
    }

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

    // public void agregarDocumento(int id, int idDocumento) throws Exception{
    //     Alquiler alquiler = alquilerRepositorio.findById(id).get();
    //     Documento documento = documentoRepositorio.findById(idDocumento).get();

    //     if (alquiler == null) {
    //         throw new Exception("El alquiler especificado no existe.");
    //     }
    //     if (documento == null) {
    //         throw new Exception("El documento especificado no existe.");
    //     }

    //     alquiler.getDocumentos().add(documento);

    //     alquilerRepositorio.save(alquiler);
    // }

    /**
     * Rescinde un alquiler, actualizando su estado y la disponibilidad de la propiedad.
     * Este método utiliza una nueva transacción para evitar problemas con la transacción principal.
     * 
     * @param id ID del alquiler a rescindir
     * @param fechaRescision Fecha de rescisión del alquiler
     * @throws Exception Si hay algún problema validando o rescindiendo el alquiler
     */
    @Transactional
    public void rescindirAlquiler(int id, LocalDate fechaRescision) throws Exception {
        Optional<Alquiler> alquilerOpt = alquilerRepositorio.findById(id);

        if (alquilerOpt.isEmpty()) {
            throw new Exception("El alquiler especificado no existe.");
        }

        Alquiler alquiler = alquilerOpt.get();
        
        // Inicializamos explícitamente la lista de documentos para evitar problemas con lazy loading
        // Esto asegura que los documentos existentes se mantengan en la base de datos
        if (alquiler.getDocumentos() != null) {
            alquiler.getDocumentos().size(); // Forzar la carga de la colección lazy
        }
        
        if (!"Activo".equals(alquiler.getEstado())) {
            throw new Exception("Solo se pueden rescindir alquileres activos.");
        }
        
        if (fechaRescision == null) {
            throw new Exception("La fecha de rescisión no puede ser nula.");
        }
        
        if (fechaRescision.isBefore(alquiler.getFechaIngreso())) {
            throw new Exception("La fecha de rescisión no puede ser anterior a la fecha de inicio del alquiler.");
        }
        
        if (fechaRescision.isAfter(alquiler.getFechaEgreso())) {
            throw new Exception("La fecha de rescisión no puede ser posterior a la fecha de finalización del alquiler.");
        }
        
        try {
            // Actualizamos el estado del alquiler a "Rescindido"
            alquiler.setEstado("Rescindido");
            alquiler.setFechaRecision(fechaRescision);
            
            // Cambiamos el estado de la propiedad a "Disponible"
            Propiedad propiedad = alquiler.getMiPropiedad();
            propiedad.setEstado("Disponible");
            
            // Guardamos los cambios
            propiedadRepositorio.save(propiedad);
            alquilerRepositorio.save(alquiler);
        } catch (Exception e) {
            throw new Exception("Error al rescindir el alquiler: " + e.getMessage(), e);
        }
    }

    private void validar(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial, int idPropiedad, 
                        String dniAgente, String dniInquilino, int periodoAumento, double porcentajeAumento) throws Exception {
        if (fechaIngreso == null) {
            throw new Exception("La fecha de ingreso no puede ser nula");
        }
        if (fechaEgreso == null) {
            throw new Exception("La fecha de egreso no puede ser nula");
        }
        if (!fechaEgreso.isAfter(fechaIngreso)) {
            throw new Exception("La fecha de egreso no puede ser anterior o igual a la fecha de ingreso");
        }
        if (valorInicial <= 0) {
            throw new Exception("El valor inicial debe ser un valor positivo");
        }
        if(idPropiedad == 0) {
            throw new Exception("El idPropiedad no puede ser nulo");
        }
        if (dniAgente == null || dniAgente.isEmpty()) {
            throw new Exception("El dni del agente no puede ser nulo o estar vacio");
        }
        if (dniInquilino == null || dniInquilino.isEmpty()) {
            throw new Exception("El dni del inquilino no puede ser nulo o estar vacio");
        }
        if (periodoAumento <= 0) {
            throw new Exception("El periodo del aumento debe ser un valor positivo");
        }
        if (porcentajeAumento <= 0) {
            throw new Exception("El porcentaje del aumento debe ser un valor positivo");
        }
    }

    public void agregarDocumento(int id, int idDocumento) throws Exception{
        Alquiler alquiler = alquilerRepositorio.findById(id).get();
        // Documento documento = documentoRepositorio.findById(idDocumento).get();

        if (alquiler == null) {
            throw new Exception("El alquiler especificado no existe.");
        }
        // if (documento == null) {
        //     throw new Exception("El documento especificado no existe.");
        // }

        // alquiler.getDocumentos().add(documento);

        alquilerRepositorio.save(alquiler);
    }

    // private void validar(LocalDate fechaIngreso, LocalDate fechaEgreso, double valorInicial, int idPropiedad, String dniAgente, String dniInquilino)
    //         throws Exception {
    //     if (fechaIngreso == null) {
    //         throw new Exception("La fecha de ingreso no puede ser nula");
    //     }
    //     if (fechaEgreso == null) {
    //         throw new Exception("La fecha de egreso no puede ser nula");
    //     }
    //     if (!fechaEgreso.isAfter(fechaIngreso)) {
    //         throw new Exception("La fecha de egreso no puede ser anterior o igual a la fecha de ingreso");
    //     }
    //     if (valorInicial <= 0) {
    //         throw new Exception("El valor inicial debe ser un valor positivo");
    //     }
    //     if(idPropiedad == 0) {
    //         throw new Exception("El idPropiedad no puede ser nulo");
    //     }
    //     if (dniAgente == null || dniAgente.isEmpty()) {
    //         throw new Exception("El dni del agente no puede ser nulo o estar vacio");
    //     }
    //     if (dniInquilino == null || dniInquilino.isEmpty()) {
    //         throw new Exception("El dni del inquilino no puede ser nulo o estar vacio");
    //     }
    // }

}