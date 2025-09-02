package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
}
