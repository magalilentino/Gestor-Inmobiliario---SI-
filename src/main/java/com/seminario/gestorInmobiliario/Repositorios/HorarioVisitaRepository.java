package com.seminario.gestorInmobiliario.Repositorios;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.seminario.gestorInmobiliario.Entidades.HorarioVisita;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;

@Repository
public interface HorarioVisitaRepository extends JpaRepository<HorarioVisita, Integer> {

    // Devuelve todos los horarios disponibles para una propiedad
    List<HorarioVisita> findByPropiedadAndDisponibleTrue(Propiedad propiedad);

    // Buscar horarios que se superponen con un nuevo horario propuesto
    @Query("SELECT h FROM HorarioVisita h "
            + "WHERE h.propiedad.idPropiedad = :idPropiedad "
            + "AND h.hora_ini < :horaFin "
            + // El inicio existente es ANTES de que termine el nuevo
            "AND h.hora_fin > :horaIni")     // El fin existente es DESPUÉS de que empiece el nuevo
    public List<HorarioVisita> findSolapados(
            @Param("idPropiedad") Integer idPropiedad,
            @Param("horaIni") LocalDateTime horaIni,
            @Param("horaFin") LocalDateTime horaFin
    );

    /**
     * * Busca horarios para una LISTA de propiedades y los devuelve ordenados.
     */
    @Query("""
        SELECT h FROM HorarioVisita h 
        WHERE h.propiedad IN :propiedades 
        AND h.disponible = true 
        ORDER BY h.propiedad.idPropiedad ASC, h.fecha ASC, h.hora_ini ASC
    """)
    List<HorarioVisita> findHorariosDisponiblesOrdenados(
            @Param("propiedades") List<Propiedad> propiedades
    );

    /* Busca TODOS los horarios disponibles y los devuelve ordenados.
     */
    @Query("""
        SELECT h FROM HorarioVisita h 
        WHERE h.disponible = true 
        ORDER BY h.propiedad.idPropiedad ASC, h.fecha ASC, h.hora_ini ASC
    """)
    List<HorarioVisita> findAllDisponiblesOrdenados();

}
