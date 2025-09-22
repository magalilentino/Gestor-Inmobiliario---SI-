package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.VisitaPropiedad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitaPropiedadRepository extends JpaRepository<VisitaPropiedad, Integer> {
}
