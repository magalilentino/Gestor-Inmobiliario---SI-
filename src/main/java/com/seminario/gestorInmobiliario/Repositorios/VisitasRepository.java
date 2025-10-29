package com.seminario.gestorInmobiliario.Repositorios;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seminario.gestorInmobiliario.Entidades.Visitas;

@Repository
public interface VisitasRepository extends JpaRepository<Visitas, Integer> {

    /**
     * Cuenta cuántas visitas se agendaron en un rango de fechas (ej. este mes).
     */
    @Query("SELECT COUNT(v) FROM Visitas v WHERE v.fecha BETWEEN :inicio AND :fin")
    long contarVisitasEnRango(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);

    /**
     * Obtiene el Top 5 de propiedades más visitadas en un rango de fechas.
     */
    @Query(value = "SELECT v.id_propiedad.ubicacion, COUNT(v.nroVisita) AS totalVisitas " +
                   "FROM Visitas v WHERE v.fecha BETWEEN :inicio AND :fin " +
                   "GROUP BY v.id_propiedad.ubicacion " +
                   "ORDER BY totalVisitas DESC")
    List<Object[]> findTopPropiedadesVisitadasEnRango(@Param("inicio") LocalDate inicio, @Param("fin") LocalDate fin);
}
