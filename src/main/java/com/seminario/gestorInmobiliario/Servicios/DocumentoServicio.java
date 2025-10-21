package com.seminario.gestorInmobiliario.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Documento;
import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;
import com.seminario.gestorInmobiliario.Repositorios.DocumentoRepository;

@Service
public class DocumentoServicio {

    @Autowired
    private DocumentoRepository documentoRepositorio;
    
    @Autowired
    private AlquilerRepository alquilerRepositorio;

    public List<Documento> crearDocumentos(String[] descripciones, MultipartFile[] archivos) throws Exception {
        List<Documento> documentos = new ArrayList<>();

        for (int i = 0; i < archivos.length; i++) {
            MultipartFile archivo = archivos[i];
            String descripcion = descripciones[i];

            if (!archivo.isEmpty()) {
                Documento doc = new Documento();
                doc.setDescripcion(descripcion);
                doc.setArchivo(archivo.getBytes());

                // NO se guarda en BD hasta que se confirme el registro del alquiler
                // Los documentos se mantienen en memoria y se guardan cuando se crea el alquiler
                documentos.add(doc);
            }
        }

        return documentos;
    }


    // @Transactional
    // public void crearDocumento(String enlace, String descripcion, byte[] contenido, Integer idAlquiler)
    //         throws Exception {
    //     validar(enlace, idAlquiler);

    //     Alquiler alquiler = alquilerRepositorio.findById(idAlquiler)
    //             .orElseThrow(() -> new Exception("El alquiler especificado no existe."));

    //     Documento documento = new Documento();
    //     documento.setDescripcion(descripcion);
    //     documento.setContenido(contenido);
    //     documento.setAlquiler(alquiler);

    //     documentoRepositorio.save(documento);
    // }

    // @Transactional
    // public List<Documento> crearDocumentos(String[] descripciones, MultipartFile[] archivos, Integer idAlquiler) throws Exception {
    //     List<Documento> documentos = new ArrayList<>();

    //     Alquiler alquiler = alquilerRepositorio.findById(idAlquiler)
    //             .orElseThrow(() -> new Exception("El alquiler especificado no existe."));

    //     for (int i = 0; i < archivos.length; i++) {
    //         MultipartFile archivo = archivos[i];
    //         String descripcion = descripciones[i];

    //         if (!archivo.isEmpty()) {
    //             Documento doc = new Documento();
    //             doc.setDescripcion(descripcion);
    //             doc.setContenido(archivo.getBytes());
    //             doc.setAlquiler(alquiler);

    //             documentoRepositorio.save(doc);
    //             documentos.add(doc);
    //         }
    //     }

    //     return documentos;
    // }

    @Transactional(readOnly = true)
    public List<Documento> listarDocumentos() {
        return documentoRepositorio.findAll();
    }

    @Transactional
    public void modificarDocumento(Integer idDocumento, String descripcion, MultipartFile archivo) throws Exception {
        Documento documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new Exception("El documento especificado no existe."));

        if (descripcion != null && !descripcion.trim().isEmpty()) {
            documento.setDescripcion(descripcion);
        }

        if (archivo != null && !archivo.isEmpty()) {
            documento.setArchivo(archivo.getBytes());
        }

        documentoRepositorio.save(documento);
    }

    @Transactional(readOnly = true)
    public Documento getOne(Integer idDocumento) {
        return documentoRepositorio.getReferenceById(idDocumento);
    }
    
    /**
     * Guarda un documento de rescisión de alquiler en la base de datos.
     * Este método utiliza una nueva transacción para evitar problemas con la transacción principal.
     * 
     * @param archivo Contenido del archivo en bytes
     * @param descripcion Descripción del documento
     * @param idAlquiler ID del alquiler relacionado
     * @return El documento guardado
     * @throws Exception Si hay algún problema validando o guardando el documento
     */
    @Transactional
    public Documento guardarFormularioRescision(byte[] archivo, String descripcion, Integer idAlquiler) throws Exception {
        if (archivo == null || archivo.length == 0) {
            throw new Exception("El archivo no puede estar vacío");
        }
        
        if (descripcion == null || descripcion.isEmpty()) {
            throw new Exception("La descripción no puede estar vacía");
        }
        
        if (idAlquiler == null) {
            throw new Exception("El ID del alquiler no puede ser nulo");
        }
        
        try {
            // Verificamos que el alquiler exista antes de crear el documento
            if (!alquilerRepositorio.existsById(idAlquiler)) {
                throw new Exception("El alquiler con ID " + idAlquiler + " no existe");
            }
            
            // Usamos getReferenceById que crea un proxy sin cargar la entidad completa
            // Esto evita problemas transaccionales y es más eficiente
            Alquiler alquilerProxy = alquilerRepositorio.getReferenceById(idAlquiler);
            
            // Creamos el documento y lo asociamos con el proxy del alquiler
            Documento documento = new Documento();
            documento.setArchivo(archivo);
            documento.setDescripcion(descripcion);
            documento.setAlquiler(alquilerProxy); // Establecemos la FK sin cargar toda la entidad
            
            // Guardamos el documento - JPA solo necesita el ID para establecer la FK
            Documento documentoGuardado = documentoRepositorio.save(documento);
            
            return documentoGuardado;
        } catch (Exception e) {
            throw new Exception("Error al guardar el documento de rescisión: " + e.getMessage(), e);
        }
    }
}
