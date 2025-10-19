package com.seminario.gestorInmobiliario.Entidades;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "horarios_visita")
public class HorarioVisita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario", nullable = false, unique = true, length = 11, columnDefinition = "INT(11) UNSIGNED")
    private int idHorario;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate fecha;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime hora_ini;

    @Column(nullable = false, columnDefinition = "TIME")
    private LocalTime hora_fin;

    @Column(nullable = false)
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "id_propiedad", nullable = false)
    private Propiedad propiedad;

    // Constructor vacío requerido por JPA
    public HorarioVisita() {}

    // Constructor con parámetros
    public HorarioVisita(LocalDate fecha, LocalTime hora_ini,LocalTime hora_fin, boolean disponible, Propiedad propiedad) {
        this.fecha = fecha;
        this.hora_ini = hora_ini;
        this.hora_fin = hora_fin;
        this.disponible = disponible;
        this.propiedad = propiedad;
    }

    // Getters y Setters
    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraIni() {
        return hora_ini;
    }

    public void setHoraIni(LocalTime hora_ini) {
        this.hora_ini = hora_ini;
    }

        public LocalTime getHoraFin() {
        return hora_fin;
    }

    public void setHoraFin(LocalTime hora_fin) {
        this.hora_fin = hora_fin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }
}
