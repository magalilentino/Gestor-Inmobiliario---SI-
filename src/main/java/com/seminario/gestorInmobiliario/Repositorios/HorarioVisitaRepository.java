package com.seminario.gestorInmobiliario.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seminario.gestorInmobiliario.Entidades.HorarioVisita;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;

public interface HorarioVisitaRepository extends JpaRepository<HorarioVisita, Integer> {

    // Devuelve todos los horarios disponibles para una propiedad
    List<HorarioVisita> findByPropiedadAndDisponibleTrue(Propiedad propiedad);
}
