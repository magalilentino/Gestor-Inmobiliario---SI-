package com.seminario.gestorInmobiliario.repository;

import com.seminario.gestorInmobiliario.Entidades.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
}
