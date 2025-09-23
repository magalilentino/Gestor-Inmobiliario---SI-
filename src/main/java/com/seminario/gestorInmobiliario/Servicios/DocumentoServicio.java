package com.seminario.gestorInmobiliario.Servicios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.seminario.gestorInmobiliario.Entidades.Documento;
import com.seminario.gestorInmobiliario.Repositorios.DocumentoRepository;

@Service
public class DocumentoServicio {

    @Autowired
    private DocumentoRepository documentoRepositorio;

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

    @Transactional(readOnly = true)
    public List<Documento> listarDocumentos() {
        return documentoRepositorio.findAll();
    }

    @Transactional
    public void modificarDocumento(Integer idDocumento, String descripcion, MultipartFile archivo) throws Exception {

        Documento documento = documentoRepositorio.findById(idDocumento)
                .orElseThrow(() -> new Exception("El documento especificado no existe."));

        documento.setArchivo(archivo.getBytes());
        documento.setDescripcion(descripcion);

        documentoRepositorio.save(documento);
    }

    @Transactional(readOnly = true)
    public Documento getOne(Integer idDocumento) {
        return documentoRepositorio.getReferenceById(idDocumento);
    }

}
