package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Inquilino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InquilinoRepository extends JpaRepository<Inquilino, String> {
}
