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
                        @RequestParam(required = false) String dpto,
                        @RequestParam String medidas,
                        @RequestParam Integer cantAmbientes,
                        @RequestParam Integer idLocalidad,
                        @RequestParam Integer idCategoria,
                        @RequestParam(required = false) MultipartFile imagen,
                        ModelMap model) {
        try {
            byte[] contenido = imagen != null ? imagen.getBytes() : null;
            String ubicacion =  calle+' '+altura+','+piso+','+dpto;
            propiedadServicio.crearPropiedad(0, ubicacion, medidas, cantAmbientes, idLocalidad, idCategoria, contenido);
            model.put("exito", "La propiedad fue cargada correctamente!");
            return "redirect:/propiedad/listar";
        } catch (Exception ex) {
            model.put("error", ex.getMessage());
            return "propiedad/form";
        }
    }

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