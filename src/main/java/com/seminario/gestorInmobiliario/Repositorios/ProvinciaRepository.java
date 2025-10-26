package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Provincia;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {


    @Query("SELECT p FROM Provincia p WHERE LOWER(p.nombre) = LOWER(:nombre)")
    Provincia findByNombre(@Param("nombre") String nombre);

}
