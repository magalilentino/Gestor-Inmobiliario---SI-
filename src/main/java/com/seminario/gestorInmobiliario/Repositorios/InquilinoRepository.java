package com.seminario.gestorInmobiliario.repository;

import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
}
