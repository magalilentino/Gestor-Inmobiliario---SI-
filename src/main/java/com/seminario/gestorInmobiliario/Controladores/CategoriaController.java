package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.seminario.gestorInmobiliario.Entidades.Categoria;
import com.seminario.gestorInmobiliario.Servicios.CategoriaServicio;

@Controller
@RequestMapping("/categoria")
public class CategoriaController {
    
    @Autowired
    private CategoriaServicio categoriaServicio;
    
    // GET: Mostrar formulario de registro
    @GetMapping("/registrar")
    public String registrar() {
        return "categoria/form";
    }
    
    // POST: Registrar nueva categoría
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,
                          @RequestParam(required = false) String descripcion,
                          ModelMap model) {
        try {
            categoriaServicio.crearCategoria(nombre, descripcion);
            model.put("exito", "La categoría fue cargada correctamente.");
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return "categoria/form";
    }
    
    // GET: Listar todas las categorías
    @GetMapping("/listar")
    public String listarCategorias(ModelMap model) {
        try {
            List<Categoria> categorias = categoriaServicio.listarCategorias();
            model.put("categorias", categorias);
            return "categoria/listarCategorias";
        } catch (Exception e) {
            model.put("error", "Error al cargar categorías: " + e.getMessage());
            return "categoria/listarCategorias";
        }
    }
}
