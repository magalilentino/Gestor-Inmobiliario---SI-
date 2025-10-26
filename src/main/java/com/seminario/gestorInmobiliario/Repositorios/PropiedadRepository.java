package com.seminario.gestorInmobiliario.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seminario.gestorInmobiliario.Entidades.Propiedad;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, Integer> {

    // Este método permite buscar por ubicación (ignorando mayúsculas/minúsculas)
    List<Propiedad> findByUbicacionContainingIgnoreCase(String ubicacion);

    List<Propiedad> findByEstadoIgnoreCase(String estado);

}
