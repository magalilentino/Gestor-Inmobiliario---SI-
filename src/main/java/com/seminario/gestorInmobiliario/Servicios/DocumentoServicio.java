package com.seminario.gestorInmobiliario.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seminario.gestorInmobiliario.Entidades.Documento;
import com.seminario.gestorInmobiliario.Repositorios.DocumentoRepository;

// @Service
// public class DocumentoServicio {

//     @Autowired
//     private DocumentoRepository documentoRepositorio;

<<<<<<< HEAD
//     @Autowired
//     private AlquilerRepository alquilerRepositorio;

//     @Transactional
//     public void crearDocumento(String enlace, String descripcion, byte[] contenido, Integer idAlquiler)
//             throws Exception {

//         validar(enlace, idAlquiler);

//         Alquiler alquiler = alquilerRepositorio.findById(idAlquiler)
//                 .orElseThrow(() -> new Exception("El alquiler especificado no existe."));

//         Documento documento = new Documento();
//         documento.setEnlace(enlace);
//         documento.setDescripcion(descripcion);
//         documento.setContenido(contenido);

//         documentoRepositorio.save(documento);
//     }
=======
    public List<Documento> crearDocumentos(String[] descripciones, MultipartFile[] archivos) throws Exception {
        List<Documento> documentos = new ArrayList<>();

        for (int i = 0; i < archivos.length; i++) {
            MultipartFile archivo = archivos[i];
            String descripcion = descripciones[i];

            if (!archivo.isEmpty()) {
                Documento doc = new Documento();
                doc.setDescripcion(descripcion);
                doc.setArchivo(archivo.getBytes());

                documentoRepositorio.save(doc); // se guarda sin alquiler por ahora
                documentos.add(doc);
            }
        }

        return documentos;
    }
>>>>>>> origin/CUU02-AlquilarPropiedad

//     @Transactional(readOnly = true)
//     public List<Documento> listarDocumentos() {
//         return documentoRepositorio.findAll();
//     }

<<<<<<< HEAD
//     @Transactional
//     public void modificarDocumento(Integer idDocumento, String enlace, String descripcion, byte[] contenido,
//             Integer idAlquiler) throws Exception {

//         validar(enlace, idAlquiler);
=======
    @Transactional
    public void modificarDocumento(Integer idDocumento, String descripcion, MultipartFile archivo) throws Exception {
>>>>>>> origin/CUU02-AlquilarPropiedad

//         Documento documento = documentoRepositorio.findById(idDocumento)
//                 .orElseThrow(() -> new Exception("El documento especificado no existe."));

<<<<<<< HEAD
//         Alquiler alquiler = alquilerRepositorio.findById(idAlquiler)
//                 .orElseThrow(() -> new Exception("El alquiler especificado no existe."));

//         documento.setEnlace(enlace);
//         documento.setDescripcion(descripcion);
//         documento.setContenido(contenido);
=======
        documento.setArchivo(archivo.getBytes());
        documento.setDescripcion(descripcion);
>>>>>>> origin/CUU02-AlquilarPropiedad

//         documentoRepositorio.save(documento);
//     }

//     @Transactional(readOnly = true)
//     public Documento getOne(Integer idDocumento) {
//         return documentoRepositorio.getReferenceById(idDocumento);
//     }

<<<<<<< HEAD
//     private void validar(String enlace, Integer idAlquiler) throws Exception {
//         if (enlace == null || enlace.trim().isEmpty()) {
//             throw new Exception("El enlace no puede ser nulo o estar vacío.");
//         }
//         if (idAlquiler == null) {
//             throw new Exception("El idAlquiler no puede ser nulo.");
//         }
//     }
// }
=======
}
>>>>>>> origin/CUU02-AlquilarPropiedad
