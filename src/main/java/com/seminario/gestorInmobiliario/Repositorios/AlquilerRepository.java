package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Alquiler;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
    
    @Query("SELECT a FROM Alquiler a WHERE a.miInquilino.dniInquilino = :dni AND a.estado = :estado")
    List<Alquiler> findAlquileresByDniAndEstado(@Param("dni") String dni, @Param("estado") String estado);
}
