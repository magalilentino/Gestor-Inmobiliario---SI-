package com.seminario.gestorInmobiliario.Repositorios;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.seminario.gestorInmobiliario.Entidades.HorarioVisita;
import com.seminario.gestorInmobiliario.Entidades.Propiedad;

public interface HorarioVisitaRepository extends JpaRepository<HorarioVisita, Integer> {

    // Devuelve todos los horarios disponibles para una propiedad
    List<HorarioVisita> findByPropiedadAndDisponibleTrue(Propiedad propiedad);


     // Buscar horarios que se superponen con un nuevo horario propuesto
@Query("""
SELECT h FROM HorarioVisita h 
WHERE h.propiedad.id = :idPropiedad 
AND h.fecha = :fecha 
AND ((:horaIni BETWEEN h.hora_ini AND h.hora_fin)
  OR (:horaFin BETWEEN h.hora_ini AND h.hora_fin)
  OR (h.hora_ini BETWEEN :horaIni AND :horaFin))
""")
List<HorarioVisita> findSolapados(@Param("idPropiedad") Integer idPropiedad,
                                  @Param("fecha") LocalDate fecha,
                                  @Param("horaIni") LocalTime horaIni,
                                  @Param("horaFin") LocalTime horaFin);


/*** Busca horarios para una LISTA de propiedades y los devuelve ordenados.*/
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


