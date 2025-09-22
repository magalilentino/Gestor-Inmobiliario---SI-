package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Integer> {
}
