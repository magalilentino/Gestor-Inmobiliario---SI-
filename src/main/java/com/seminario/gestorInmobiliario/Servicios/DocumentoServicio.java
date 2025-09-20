package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void crearDocumento(String enlace, String descripcion, byte[] contenido, Integer idAlquiler)
            throws Exception {

        validar(enlace, idAlquiler);

        Alquiler alquiler = alquilerRepositorio.findById(idAlquiler)
                .orElseThrow(() -> new Exception("El alquiler especificado no existe."));

        Documento documento = new Documento();
        documento.setEnlace(enlace);
        documento.setDescripcion(descripcion);
        documento.setContenido(contenido);

        documentoRepositorio.save(documento);
    }

    @Transactional(readOnly = true)
    public List<Documento> listarDocumentos() {
        return documentoRepositorio.findAll();
    }

    @Transactional
    public void modificarDocumento(Integer idDocumento, String enlace, String descripcion, byte[] contenido,
            Integer idAlquiler) throws Exception {

        validar(enlace, idAlquiler);

        Documento documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new Exception("El documento especificado no existe."));

        Alquiler alquiler = alquilerRepositorio.findById(idAlquiler)
                .orElseThrow(() -> new Exception("El alquiler especificado no existe."));

        documento.setEnlace(enlace);
        documento.setDescripcion(descripcion);
        documento.setContenido(contenido);

        documentoRepositorio.save(documento);
    }

    @Transactional(readOnly = true)
    public Documento getOne(Integer idDocumento) {
        return documentoRepositorio.getReferenceById(idDocumento);
    }

    private void validar(String enlace, Integer idAlquiler) throws Exception {
        if (enlace == null || enlace.trim().isEmpty()) {
            throw new Exception("El enlace no puede ser nulo o estar vacío.");
        }
        if (idAlquiler == null) {
            throw new Exception("El idAlquiler no puede ser nulo.");
        }
    }
}
