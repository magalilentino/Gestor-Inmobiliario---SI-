package com.seminario.gestorInmobiliario.Repositorios;

import com.seminario.gestorInmobiliario.Entidades.Pago;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    
    @Query("SELECT p FROM Pago p WHERE p.alquiler.id = :idAlquiler AND p.estado = :estado")
    List<Pago> findPagosPendientesPorAlquiler(@Param("idAlquiler") int idAlquiler, @Param("estado") String estado);

    @Query("SELECT COUNT(p) > 0 FROM Pago p WHERE p.miAlquiler.id = :id AND MONTH(p.fechaLimite) = :mes AND YEAR(p.fechaLimite) = :anio")
    boolean existsByAlquilerIdAndMes(@Param("id") int id, @Param("mes") int mes, @Param("anio") int anio);
}
