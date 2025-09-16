package com.seminario.gestorInmobiliario.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seminario.gestorInmobiliario.Entidades.Propiedad;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
}
