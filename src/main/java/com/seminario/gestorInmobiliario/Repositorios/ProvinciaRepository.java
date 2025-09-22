package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
}
