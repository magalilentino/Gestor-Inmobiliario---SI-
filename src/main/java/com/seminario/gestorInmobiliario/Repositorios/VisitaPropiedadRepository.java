package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import com.seminario.gestorInmobiliario.Entidades.VisitaPropiedad;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaPropiedadRepository extends JpaRepository<VisitaPropiedad, Integer> {
    @Query("SELECT vp.propiedad FROM VisitaPropiedad vp " +
       "JOIN vp.visita v " +
       "WHERE v.email = :email ")
      // +"AND vp.propiedad.estado = 'Disponible'")
    List<Propiedad> listarPropVisitadas(@Param("email") String email);
}
