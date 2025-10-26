package com.seminario.gestorInmobiliario.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seminario.gestorInmobiliario.Entidades.HorarioVisita;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;

@Repository
public interface HorarioVisitaRepository extends JpaRepository<HorarioVisita, Integer> {

    // Devuelve todos los horarios disponibles para una propiedad
    List<HorarioVisita> findByPropiedadAndDisponibleTrue(Propiedad propiedad);
}
