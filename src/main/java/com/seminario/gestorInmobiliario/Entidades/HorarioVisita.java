package com.seminario.gestorInmobiliario.Entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "horarios_visita")
public class HorarioVisita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario", nullable = false, unique = true, length = 11, columnDefinition = "INT(11) UNSIGNED")
    private int idHorario;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate fecha;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime hora_ini;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime hora_fin;

    @Column(nullable = false)
    private boolean disponible;

    @Transient // Ignorado por la BBDD, solo para el formulario
    private LocalTime horaIniForm;

    @Transient // Ignorado por la BBDD, solo para el formulario
    private LocalTime horaFinForm;

    @ManyToOne
    @JoinColumn(name = "id_propiedad", nullable = false)
    private Propiedad propiedad;

    // Constructor vacío requerido por JPA
    public HorarioVisita() {
    }

    // Constructor con parámetros
    public HorarioVisita(LocalDate fecha, LocalDateTime hora_ini, LocalDateTime hora_fin, boolean disponible, Propiedad propiedad) {
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

    public LocalDateTime getHoraIni() {
        return hora_ini;
    }

    public void setHoraIni(LocalDateTime hora_ini) {
        this.hora_ini = hora_ini;
    }

    public LocalDateTime getHoraFin() {
        return hora_fin;
    }

    public void setHoraFin(LocalDateTime hora_fin) {
        this.hora_fin = hora_fin;
    }

    public LocalTime getHoraIniForm() {
        // Si el campo real tiene un valor, lo usamos para rellenar el form
        return (hora_ini != null) ? hora_ini.toLocalTime() : horaIniForm;
    }

    public void setHoraIniForm(LocalTime horaIniForm) {
        this.horaIniForm = horaIniForm;
    }

    public LocalTime getHoraFinForm() {
        return (hora_fin != null) ? hora_fin.toLocalTime() : horaFinForm;
    }

    public void setHoraFinForm(LocalTime horaFinForm) {
        this.horaFinForm = horaFinForm;
    }

    public boolean getDisponible() {
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
