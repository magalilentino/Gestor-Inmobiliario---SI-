package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    // GET: Mostrar página con formulario + listado
    @GetMapping("/registrar")
    public String registrar(ModelMap model) {
        List<Categoria> categorias = categoriaServicio.listarCategorias();
        model.put("categorias", categorias);
        return "categoria/registrar";
    }
    
    // POST: Registrar nueva categoría
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,
                          @RequestParam(required = false) String descripcion,
                          ModelMap model) {
        try {
            categoriaServicio.crearCategoria(nombre, descripcion);
            model.put("exito", "Categoría registrada correctamente");
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        
        // Recargar lista
        List<Categoria> categorias = categoriaServicio.listarCategorias();
        model.put("categorias", categorias);
        return "categoria/registrar";
    }
    
    // POST: Modificar categoría existente
    @PostMapping("/modificar")
    public String modificar(@RequestParam int id,
                           @RequestParam String nombre,
                           @RequestParam(required = false) String descripcion,
                           ModelMap model) {
        try {
            categoriaServicio.modificarCategoria(nombre, descripcion, id);
            model.put("exito", "Categoría modificada correctamente");
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        
        // Recargar lista
        List<Categoria> categorias = categoriaServicio.listarCategorias();
        model.put("categorias", categorias);
        return "categoria/registrar";
    }
    
    // POST: Eliminar categoría (opcional)
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id, ModelMap model) {
        try {
            categoriaServicio.eliminarCategoria(id);
            model.put("exito", "Categoría eliminada correctamente");
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        
        List<Categoria> categorias = categoriaServicio.listarCategorias();
        model.put("categorias", categorias);
        return "categoria/registrar";
    }
}
