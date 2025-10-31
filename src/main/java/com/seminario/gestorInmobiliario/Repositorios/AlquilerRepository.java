package com.seminario.gestorInmobiliario.Repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seminario.gestorInmobiliario.Entidades.Alquiler;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
    
    @Query("SELECT a FROM Alquiler a WHERE a.miInquilino.dniInquilino = :dni AND a.estado = :estado")
    List<Alquiler> findAlquileresByDniAndEstado(@Param("dni") String dni, @Param("estado") String estado);

    @Query("SELECT a FROM Alquiler a WHERE a.estado = :estado")
    List<Alquiler> findByEstado(@Param("estado") String estado);

    /**
     * Suma el valor de todos los alquileres que están actualmente "Activos".
     */
    @Query("SELECT SUM(a.valorInicial) FROM Alquiler a WHERE a.estado = 'Activo'")
    Double sumarIngresosActivos();

    /**
     * Cuenta cuántos alquileres nuevos se firmaron en un rango de fechas (ej. este mes).
     */
    @Query("SELECT COUNT(a) FROM Alquiler a WHERE a.fechaIngreso BETWEEN :inicio AND :fin")
    long contarAlquileresNuevosEnRango(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    /**
     * Obtiene el ranking de agentes: Nombre, Nro de alquileres cerrados, y Suma de ingresos.
     * Usado para el gráfico de barras.
     */
    @Query("SELECT a.miAgente.nomApe, COUNT(a.idAlquiler), SUM(a.valorInicial) " +
           "FROM Alquiler a WHERE a.estado = 'Activo' " +
           "GROUP BY a.miAgente.nomApe " +
           "ORDER BY SUM(a.valorInicial) DESC")
    List<Object[]> findRendimientoAgentesActivos();
}
