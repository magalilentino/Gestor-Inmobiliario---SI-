package com.seminario.gestorInmobiliario.Repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seminario.gestorInmobiliario.Entidades.Propiedad;

@Repository
public interface PropiedadRepository extends JpaRepository<Propiedad, Integer> {

    // Este método permite buscar por ubicación (ignorando mayúsculas/minúsculas)
    List<Propiedad> findByUbicacionContainingIgnoreCase(String ubicacion);

    List<Propiedad> findByEstadoIgnoreCase(String estado);

    /**
     * Cuenta cuántas propiedades hay de un estado específico (ej. "Disponible")
     */
    @Query("SELECT COUNT(p) FROM Propiedad p WHERE p.estado = :estado")
    long contarPorEstado(@Param("estado") String estado);

    /**
     * Obtiene una lista con el conteo de propiedades agrupadas por su estado.
     * Usado para el gráfico de torta.
     */
    @Query("SELECT p.estado, COUNT(p) FROM Propiedad p GROUP BY p.estado")
    List<Object[]> contarTotalPorEstado();

}
