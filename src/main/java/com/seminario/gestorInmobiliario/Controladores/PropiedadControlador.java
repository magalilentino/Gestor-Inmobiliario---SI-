package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.seminario.gestorInmobiliario.Entidades.Categoria;
import com.seminario.gestorInmobiliario.Entidades.Localidad;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Entidades.Provincia;
import com.seminario.gestorInmobiliario.Servicios.CategoriaServicio;
import com.seminario.gestorInmobiliario.Servicios.LocalidadServicio;
import com.seminario.gestorInmobiliario.Servicios.PropiedadServicio;
import com.seminario.gestorInmobiliario.Servicios.ProvinciaServicio;

@Controller
@RequestMapping("/propiedad")
public class PropiedadControlador {

    @Autowired
    private PropiedadServicio propiedadServicio;

    @Autowired
    private LocalidadServicio localidadServicio;

    @Autowired
    private CategoriaServicio categoriaServicio;

    @Autowired
    private ProvinciaServicio provinciaServicio;

    @GetMapping("/registrar") // localhost:8080/propiedad/registrar
    public String registrar(ModelMap model) {
        // List<Localidad> localidades = localidadServicio.listarLocalidades();
        List<Provincia> provincias = provinciaServicio.listarProvincias();
        List<Categoria> categorias = categoriaServicio.listarCategorias();
        // model.addAttribute("localidades", localidades);
        model.addAttribute("categorias", categorias);
        model.addAttribute("provincias", provincias);
        return "propiedad/form";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String calle,
                        @RequestParam String altura,
                        @RequestParam(required = false) String piso,
                        @RequestParam(required = false) String depto,
                        @RequestParam String medidas,
                        @RequestParam int cantAmbientes,
                        @RequestParam(required = false) Integer idLocalidad,
                        @RequestParam Integer idCategoria,
                        @RequestParam(required = false) MultipartFile archivo,
                        ModelMap model) {
        try {
            byte[] contenido = archivo != null ? archivo.getBytes() : null;
            String ubicacion = calle + ' ' + altura + ',' + piso + ',' + depto;
            if (piso == null || piso.isEmpty() || depto == null || depto.isEmpty()) {
                ubicacion =  calle + ' ' + altura ;
            }
            
            propiedadServicio.crearPropiedad( ubicacion, medidas, cantAmbientes, idLocalidad, idCategoria, contenido);
            model.put("exito", "La propiedad fue cargada correctamente!");
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "propiedad/form";
        }
        return "index";
    }

    @GetMapping("/imagen/{id}")
    public ResponseEntity<byte[]> imagenPropiedad(@PathVariable int id) {
        Propiedad propiedad = propiedadServicio.getOne(id);
        byte[] imagen = propiedad.getContenido();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

//    @GetMapping("/imagen/{idPropiedad}")
//     public void imagenPropiedad(
//         @PathVariable Integer idPropiedad, 
//         ModelMap modelo, // ModelMap se puede incluir, aunque no es estrictamente necesario para el output stream
//         HttpServletResponse response // Necesitamos el objeto de respuesta HTTP
//     ) {
//         try {
//             Propiedad propiedad = propiedadServicio.getOne(idPropiedad); 
//             byte[] imagenBytes = propiedad.getContenido();

//             if (imagenBytes == null || imagenBytes.length == 0) {
//                 response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404
//                 return;
//             }

//             // 2. Configurar las cabeceras HTTP
//             // Configurar el tipo de contenido
//             // ¡IMPORTANTE! Asume que la imagen es PNG. Si tienes JPEGs también, deberías guardar el Content-Type en la DB.
//             response.setContentType("image/jpg"); 
//             // Configurar la longitud del contenido
//             response.setContentLength(imagenBytes.length); 

//             // 3. Escribir los bytes de la imagen directamente al stream de respuesta
//             OutputStream out = response.getOutputStream();
//             out.write(imagenBytes);
//             out.flush();
            
//         } catch (Exception e) {
//             // Manejar errores (ej. propiedad no encontrada, error de DB)
//             try {
//                 // Devolver un error 500 si hay un fallo interno
//                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
//             } catch (Exception ex) {
//                 // Omitir si la respuesta ya está comprometida
//             }
//         }
//     }


    @GetMapping("/modificar")
    public String modificar(@RequestParam Integer id, ModelMap model) {
        try {
            List<Localidad> localidades = localidadServicio.listarLocalidades();
            List<Categoria> categorias = categoriaServicio.listarCategorias();
            model.addAttribute("localidades", localidades);
            model.addAttribute("categorias", categorias);
            model.addAttribute("propiedad", propiedadServicio.getOne(id));
            return "propiedad/modificar";
        } catch (Exception e) {
            return "redirect:/propiedad/listar";
        }
    }

    @PostMapping("/modificar")
    public String modificar(@RequestParam Integer id,
                        @RequestParam String ubicacion,
                        @RequestParam String medidas,
                        @RequestParam Integer cantAmbientes,
                        @RequestParam Integer idLocalidad,
                        @RequestParam Integer idCategoria,
                        @RequestParam(required = false) MultipartFile imagen,
                        ModelMap model) {
        try {
            byte[] contenido = imagen != null ? imagen.getBytes() : null;
            propiedadServicio.modificarPropiedad(id, ubicacion, medidas, cantAmbientes, idLocalidad, idCategoria, contenido);
            model.put("exito", "La propiedad fue modificada correctamente!");
            return "redirect:/propiedad/listar";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "propiedad/modificar";
        }
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        List<Propiedad> propiedades = propiedadServicio.listarPropiedades();
        model.addAttribute("propiedades", propiedades);
        return "propiedad/listar";
    }

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam Integer id, ModelMap model) {
        try {
            propiedadServicio.eliminarPropiedad(id);
            model.put("exito", "La propiedad fue eliminada correctamente!");
            return "redirect:/propiedad/listar";
        } catch (Exception e) {
            model.put("error", e.getMessage());
            return "propiedad/listar";
        }
    }
}