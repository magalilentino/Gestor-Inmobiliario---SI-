package com.seminario.gestorInmobiliario.Controladores;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Servicios.AlquilerServicio;
import com.seminario.gestorInmobiliario.Servicios.InquilinoService;
import com.seminario.gestorInmobiliario.Servicios.PrecioServicio;
import com.seminario.gestorInmobiliario.Servicios.PropiedadServicio;
import com.seminario.gestorInmobiliario.Servicios.VisitaPropiedadServicio;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/alquiler")
public class AlquilerControlador {
    @Autowired
    private AlquilerServicio alquilerServicio;

    @Autowired
    private VisitaPropiedadServicio visitaPropiedadServicio;

    @Autowired
    private PropiedadServicio propiedadServicio;

    @Autowired
    private InquilinoService inquilinoService;

    @Autowired
    private PrecioServicio precioServicio;
    
    @GetMapping("/registrar") // localhost:8080/alquiler/registrar
    public String registrar() {
        return "alquiler/buscarInquilino";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam LocalDate fechaIngreso, 
                            @RequestParam LocalDate fechaEgreso, 
                            @RequestParam double valorInicial, 
                            @RequestParam int periodoAumento,
                            @RequestParam double porcentajeAumento,
                            HttpSession session, 
                            ModelMap model){
        try {
            // List<Documento> documentos = (List<Documento>) session.getAttribute("documentosPendientes");
            String dniInquilino = (String) session.getAttribute("dniInquilino");
            Agente agente = (Agente) session.getAttribute("agentesession");
            String dniAgente = agente != null ? agente.getDniAgente() : null;
            int idPropiedad = (int) session.getAttribute("idPropiedad");

            Alquiler alquilerCreado = alquilerServicio.crearAlquiler(fechaIngreso, fechaEgreso, valorInicial, idPropiedad, dniAgente, dniInquilino, periodoAumento, porcentajeAumento);

            propiedadServicio.cambiarEstadoPropiedad(idPropiedad, "Ocupada");
             
            precioServicio.crearPrecio(LocalDate.now(), idPropiedad, alquilerCreado.getIdAlquiler());

            model.put("exito", "El Alquiler fue cargado correctamente."); // falta mostrar este mensaje

        } catch (Exception ex) {
            model.put("error", ex.getMessage());

            return "alquiler/form.html"; 
        }
        return "index.html";
    }

    @GetMapping("/form") // localhost:8080/alquiler/registrar
    public String form() {
        return "alquiler/form";
    }

    @PostMapping("/buscarInquilino")
    public String buscarInquilino(@RequestParam("dniInq") String dni,
                                    HttpSession session, ModelMap model) {
    try {
        Inquilino inquilino = inquilinoService.getOne(dni);
        session.setAttribute("dniInquilino", dni); 

        String email = inquilino.getEmail();
        session.setAttribute("email", email);

        List<Propiedad> propVisitadas = visitaPropiedadServicio.listarPropVisitadas(email);
        model.put("propVisitadas", propVisitadas);

        model.put("exito", "Inquilino encontrado");
        return "propiedad/seleccionar";
    } catch (Exception e) {
        model.put("error", "No hay un inquilino con ese DNI");
        model.put("mostrarOpciones", true); // flag para mostrar el cartel
        return "alquiler/buscarInquilino";

    }
    
}
}
