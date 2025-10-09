package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Localidad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {

    @Query("SELECT l FROM Localidad l WHERE l.miProvincia.idProvincia = ?1")
    List<Localidad> buscarPorProvincia(int idProv);
    
}
