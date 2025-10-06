package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.seminario.gestorInmobiliario.Entidades.Documento;
import com.seminario.gestorInmobiliario.Servicios.DocumentoServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/documento")
public class DocumentoControlador {

    @Autowired
    private DocumentoServicio documentoServicio;

     @GetMapping("/registrar")
    public String registrar(@RequestParam int idPropiedad,
                            HttpSession session,
                            ModelMap model) {
        String email = (String) session.getAttribute("email");
        model.put("email", email);
        model.put("idPropiedad", idPropiedad);
        session.setAttribute("idPropiedad", idPropiedad);
        return "documento/form";
    }


    @PostMapping("/registro")
    public String registrarDocumentos(@RequestParam MultipartFile[] archivos,
                                    @RequestParam String[] descripciones,
                                    HttpSession session,
                                    ModelMap model) {
        try {
            List<Documento> documentos = documentoServicio.crearDocumentos(descripciones, archivos);
            session.setAttribute("documentosPendientes", documentos);
            model.put("exito", "Documentos cargados correctamente.");
            return "alquiler/form"; // siguiente paso
        } catch (Exception e) {
            model.put("error", "Error al cargar documentos: " + e.getMessage());
            return "documento/form";
        }
    }

    // @GetMapping("/registrar")
    // public String crearDocumento() {
    //     return "documento/form";
    // }

    // @PostMapping("/registro")
    // public String guardarDocumento(@RequestParam String nombre, @RequestParam MultipartFile archivo,
    //         @RequestParam(required = false) Integer idAlquiler, ModelMap modelo) {
    //     try {
    //         String descripcion = nombre;
    //         byte[] contenido = archivo.getBytes();
    //         String enlace = archivo.getOriginalFilename();
    //         documentoServicio.crearDocumento(enlace, descripcion, contenido, idAlquiler);
    //         modelo.put("exito", "El documento fue cargado correctamente!");
    //         return "redirect:/";
    //     } catch (Exception e) {
    //         modelo.put("error", e.getMessage());
    //         return "documento/form";
    //     }
    // }
}