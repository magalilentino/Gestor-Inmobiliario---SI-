package com.seminario.gestorInmobiliario.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seminario.gestorInmobiliario.Entidades.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Integer> {

    // Este método permite buscar por ubicación (ignorando mayúsculas/minúsculas)
    List<Propiedad> findByUbicacionContainingIgnoreCase(String ubicacion);

}
