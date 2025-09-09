package com.seminario.gestorInmobiliario.Servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Repositorios.CategoriaRepository;
import com.seminario.gestorInmobiliario.Entidades.Categoria;


@Service
public class CategoriaServicio {

    @Autowired
    private CategoriaRepository categoriaRepositorio;

    @Transactional
    public void crearCategoria(String nombre, String descripcion ) throws Exception {

        //validar(nombre);

        Categoria categoria = new Categoria();

        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        categoriaRepositorio.save(categoria);
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarCategorias() {
        return categoriaRepositorio.findAll();
    }

    @Transactional
    public void modificarCategoria(String nombre, String descripcion, int id) throws Exception {
        
        // validar(nombre);

        Optional<Categoria> categoriaOpt = categoriaRepositorio.findById(id);

        if (categoriaOpt.isPresent()) {
            Categoria categoria = categoriaOpt.get();

            categoria.setNombre(nombre);
            categoria.setDescripcion(descripcion);

            categoriaRepositorio.save(categoria);
        } else {
            throw new Exception("No se encontró una categoria con el ID especificado");
        }
    }

    @Transactional
    public void eliminarCategoria(int id) throws Exception{
        Optional<Categoria> categoriaOpt = categoriaRepositorio.findById(id);
        if (categoriaOpt.isPresent()) {
            categoriaRepositorio.delete(categoriaOpt.get());
        } else {
            throw new Exception("La categoria con el ID especificado no existe");
        }

    }

    @Transactional(readOnly = true)
    public Categoria getOne(int id){
        return categoriaRepositorio.getReferenceById(id);
    }


    // private void validar(String nombre) throws Exception {
    //     if (nombre.isEmpty() || nombre == null) {
    //         throw new Exception("el nombre no puede ser nulo o estar vacío");
    //     }
    // }

}