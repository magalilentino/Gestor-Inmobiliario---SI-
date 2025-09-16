package com.seminario.gestorInmobiliario.Repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seminario.gestorInmobiliario.Entidades.Inquilino;

public interface InquilinoRepository extends JpaRepository<Inquilino, Long> {
}
