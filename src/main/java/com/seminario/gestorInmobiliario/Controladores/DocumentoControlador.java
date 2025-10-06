package com.seminario.gestorInmobiliario.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.seminario.gestorInmobiliario.Servicios.DocumentoServicio;

@Controller
@RequestMapping("/documento")
public class DocumentoControlador {

    @Autowired
    private DocumentoServicio documentoServicio;

    @GetMapping("/registrar")
    public String crearDocumento() {
        return "documento/form";
    }

    @PostMapping("/registro")
    public String guardarDocumento(@RequestParam String nombre, @RequestParam MultipartFile archivo,
            @RequestParam(required = false) Integer idAlquiler, ModelMap modelo) {
        try {
            String descripcion = nombre;
            byte[] contenido = archivo.getBytes();
            String enlace = archivo.getOriginalFilename();
            documentoServicio.crearDocumento(enlace, descripcion, contenido, idAlquiler);
            modelo.put("exito", "El documento fue cargado correctamente!");
            return "redirect:/";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            return "documento/form";
        }
    }
}