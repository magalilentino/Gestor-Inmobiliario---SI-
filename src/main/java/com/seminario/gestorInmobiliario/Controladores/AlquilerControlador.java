package com.seminario.gestorInmobiliario.Controladores;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.multipart.MultipartFile;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Documento;
import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Servicios.AlquilerServicio;
import com.seminario.gestorInmobiliario.Servicios.DocumentoServicio;
import com.seminario.gestorInmobiliario.Servicios.InquilinoService;
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
    private DocumentoServicio documentoServicio;
    
    @GetMapping("/registrar") // localhost:8080/alquiler/registrar
    public String registrar() {
        return "alquiler/buscarInquilino";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam LocalDate fechaIngreso, 
                            @RequestParam LocalDate fechaEgreso, 
                            @RequestParam double valorInicial, 
                            HttpSession session, 
                            ModelMap model){
        try {
            List<Documento> documentos = (List<Documento>) session.getAttribute("documentosPendientes");
            String dniInquilino = (String) session.getAttribute("dniInquilino");
            Agente agente = (Agente) session.getAttribute("agentesession");
            String dniAgente = agente != null ? agente.getDniAgente() : null;
            int idPropiedad = (int) session.getAttribute("idPropiedad");

            alquilerServicio.crearAlquiler(fechaIngreso, fechaEgreso, valorInicial, idPropiedad, dniAgente, dniInquilino, documentos);

            propiedadServicio.cambiarEstadoPropiedad(idPropiedad, "Ocupada");

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

        // Intentar obtener propiedades visitadas
        List<Propiedad> propVisitadas = visitaPropiedadServicio.listarPropVisitadas(email);
        
        // Si no hay propiedades visitadas disponibles, obtener todas las propiedades disponibles
        if (propVisitadas == null || propVisitadas.isEmpty()) {
            propVisitadas = propiedadServicio.listarPropiedadesDisponibles();
            if (propVisitadas.isEmpty()) {
                model.put("advertencia", "No hay propiedades disponibles para alquilar");
            } else {
                model.put("info", "No se encontraron visitas previas para este inquilino. Mostrando todas las propiedades disponibles.");
            }
        }
        
        model.put("propVisitadas", propVisitadas);
        model.put("exito", "Inquilino encontrado");
        return "propiedad/seleccionar";
    } catch (Exception e) {
        model.put("error", "No hay un inquilino con ese DNI");
        model.put("mostrarOpciones", true); // flag para mostrar el cartel
        return "alquiler/buscarInquilino";
    }
}

    @GetMapping("/listar-activos")
    public String listarAlquileresActivos(@RequestParam(required = false) String busqueda, ModelMap model) {
        try {
            List<Alquiler> alquileres = alquilerServicio.listarAlquileres();
            
            // Filtramos solo los alquileres activos
            List<Alquiler> alquileresActivos = alquileres.stream()
                .filter(alq -> "Activo".equals(alq.getEstado()))
                .toList();
            
            // Si hay un término de búsqueda, filtramos los resultados
            if (busqueda != null && !busqueda.isEmpty()) {
                String busquedaLower = busqueda.toLowerCase();
                alquileresActivos = alquileresActivos.stream()
                    .filter(alq -> 
                        // Buscar por ID
                        String.valueOf(alq.getIdAlquiler()).contains(busquedaLower) ||
                        // Buscar por dirección de la propiedad
                        alq.getMiPropiedad().getUbicacionCompleta().toLowerCase().contains(busquedaLower) ||
                        // Buscar por nombre del inquilino
                        alq.getMiInquilino().getNomApe().toLowerCase().contains(busquedaLower)
                    )
                    .toList();
            }
            
            if (alquileresActivos.isEmpty()) {
                if (busqueda != null && !busqueda.isEmpty()) {
                    model.put("advertencia", "No se encontraron alquileres activos que coincidan con: '" + busqueda + "'");
                } else {
                    model.put("advertencia", "No hay alquileres activos en este momento.");
                }
            } else {
                model.put("alquileres", alquileresActivos);
            }
            
            // Mantenemos el término de búsqueda para mostrarlo en el campo
            model.put("busqueda", busqueda);
            
            return "alquiler/listarActivos";
        } catch (Exception e) {
            model.put("error", "Error al cargar los alquileres: " + e.getMessage());
            return "alquiler/listarActivos";
        }
    }
    
    @GetMapping("/rescindir-form/{id}")
    public String rescindirForm(@PathVariable int id, ModelMap model) {
        try {
            Alquiler alquiler = alquilerServicio.getOne(id);
            model.put("alquiler", alquiler);
            return "alquiler/rescindirForm";
        } catch (Exception e) {
            model.put("error", "Error al cargar el formulario de rescisión: " + e.getMessage());
            return "redirect:/alquiler/listar-activos";
        }
    }
    
    @PostMapping("/cargar-formulario/{id}")
    public String cargarFormularioRescision(@PathVariable int id, 
                                           @RequestParam("formularioRescision") MultipartFile archivo,
                                           HttpSession session,
                                           ModelMap model) {
        try {
            Alquiler alquiler = alquilerServicio.getOne(id);
            model.put("alquiler", alquiler);
            
            if (archivo.isEmpty()) {
                model.put("error", "Debe seleccionar un archivo para el formulario de rescisión");
                return "alquiler/rescindirForm"; // Permanece en la misma página en caso de error
            }
            
            // Guardamos el archivo en la sesión temporalmente
            session.setAttribute("formularioRescision_" + id, archivo.getBytes());
            session.setAttribute("formularioRescisionNombre_" + id, archivo.getOriginalFilename());
            
            // Redirigimos al mismo formulario pero con indicación de que el formulario fue cargado
            model.put("formularioCargado", true);
            model.put("nombreArchivo", archivo.getOriginalFilename());
            
            return "alquiler/rescindirForm";
        } catch (Exception e) {
            model.put("error", "Error al cargar el formulario de rescisión: " + e.getMessage());
            
            // Aseguramos que el modelo tenga el alquiler para mostrar el formulario correctamente
            try {
                Alquiler alquiler = alquilerServicio.getOne(id);
                model.put("alquiler", alquiler);
            } catch (Exception ex) {
                // Si no podemos obtener el alquiler, redirigimos al listado
                return "redirect:/alquiler/listar-activos";
            }
            
            return "alquiler/rescindirForm"; // Permanece en la misma página en caso de error
        }
    }
    
    @PostMapping("/confirmar-rescision/{id}")
    public String confirmarRescision(@PathVariable int id, 
                                    @RequestParam(name = "fechaRescision") String fechaRescisionStr, 
                                    @RequestParam(name = "confirmar", required = false) String confirmar,
                                    HttpSession session,
                                    ModelMap model) {
        try {
            // Verificamos que el usuario esté autenticado
            Agente agente = (Agente) session.getAttribute("agentesession");
            if (agente == null) {
                return "redirect:/login?error=Debe iniciar sesión para continuar";
            }
            
            // Verificamos que el usuario haya confirmado
            if (confirmar == null || !confirmar.equals("Si")) {
                // Solo cargamos el alquiler si hay un error de validación
                Alquiler alquiler = alquilerServicio.getAlquilerConDetallesCompletos(id);
                model.put("alquiler", alquiler);
                model.put("error", "Debe confirmar la rescisión para continuar");
                return "alquiler/rescindirForm"; // Permanece en la misma página
            }
            
            // Verificamos que se haya cargado un formulario previamente
            byte[] formularioBytes = (byte[]) session.getAttribute("formularioRescision_" + id);
            
            if (formularioBytes == null) {
                // Solo cargamos el alquiler si hay un error de validación
                Alquiler alquiler = alquilerServicio.getAlquilerConDetallesCompletos(id);
                model.put("alquiler", alquiler);
                model.put("error", "Debe cargar el formulario de rescisión antes de confirmar");
                return "alquiler/rescindirForm"; // Permanece en la misma página
            }
            
            LocalDate fechaRescision = LocalDate.parse(fechaRescisionStr);
            
            // CAMBIO DE ORDEN: Primero guardamos el documento ANTES de rescindir
            // Esto asegura que el documento quede asociado al alquiler antes de cambiar su estado
            String descripcion = "Formulario de Rescisión - Alquiler #" + id + " - " + fechaRescision;
            try {
                documentoServicio.guardarFormularioRescision(
                    formularioBytes, 
                    descripcion, 
                    id
                );
            } catch (Exception docEx) {
                // Si falla el guardado del documento, no continuamos con la rescisión
                // Solo cargamos el alquiler si hay un error
                Alquiler alquiler = alquilerServicio.getAlquilerConDetallesCompletos(id);
                model.put("alquiler", alquiler);
                model.put("error", "Error al guardar el formulario de rescisión: " + docEx.getMessage());
                return "alquiler/rescindirForm";
            }
            
            // Luego rescindimos el alquiler (ahora con el documento ya guardado)
            alquilerServicio.rescindirAlquiler(id, fechaRescision);
            
            // Limpiamos la sesión solo si se completa correctamente
            session.removeAttribute("formularioRescision_" + id);
            session.removeAttribute("formularioRescisionNombre_" + id);
            
            // En lugar de redirigir, mostramos una página de éxito
            model.addAttribute("exito", "El alquiler #" + id + " ha sido rescindido correctamente.");
            
            // Cargar toda la información necesaria (incluyendo relaciones lazy)
            try {
                // Obtener alquiler con relaciones inicializadas explícitamente para evitar LazyInitializationException
                Alquiler alquilerCompleto = alquilerServicio.getAlquilerConDetallesCompletos(id);
                model.addAttribute("alquiler", alquilerCompleto);
                
                // Si hay documento guardado, podemos mostrar su ID
                if (alquilerCompleto.getDocumentos() != null && !alquilerCompleto.getDocumentos().isEmpty()) {
                    // Buscar el documento más reciente (asumiendo que es el de rescisión)
                    int ultimoDocId = -1;
                    for (Documento doc : alquilerCompleto.getDocumentos()) {
                        if (doc.getDescripcion() != null && doc.getDescripcion().contains("Rescisión")) {
                            ultimoDocId = doc.getIdDocumento();
                        }
                    }
                    if (ultimoDocId != -1) {
                        model.addAttribute("documentoId", ultimoDocId);
                    }
                }
            } catch (Exception e) {
                // Si hay error obteniendo los detalles completos, al menos mostramos el éxito
                model.addAttribute("advertencia", "El alquiler fue rescindido pero no se pueden mostrar todos los detalles: " + e.getMessage());
            }
            
            return "alquiler/rescisionExitosa"; // Página específica de éxito
            
        } catch (Exception e) {
            model.put("error", "Error al rescindir el alquiler: " + e.getMessage());
            
            // Aseguramos que el modelo tenga el alquiler para mostrar el formulario correctamente
            try {
                Alquiler alquiler = alquilerServicio.getOne(id);
                model.put("alquiler", alquiler);
                
                // Si ya había cargado un documento, mantenemos la información
                byte[] formularioBytes = (byte[]) session.getAttribute("formularioRescision_" + id);
                if (formularioBytes != null) {
                    model.put("formularioCargado", true);
                    model.put("nombreArchivo", session.getAttribute("formularioRescisionNombre_" + id));
                }
            } catch (Exception ex) {
                // Si no podemos obtener el alquiler, redirigimos al listado
                return "redirect:/alquiler/listar-activos";
            }
            
            return "alquiler/rescindirForm"; // Permanece en la misma página en caso de error
        }
    }
}
