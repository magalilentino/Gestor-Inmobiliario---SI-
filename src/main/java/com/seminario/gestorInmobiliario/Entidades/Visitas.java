package com.seminario.gestorInmobiliario.Entidades;

import java.time.LocalDate;
import java.time.LocalDateTime; // Corregí un import que faltaba

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "visitas")
public class Visitas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "nro_visita", nullable = false, unique = true, length = 11, columnDefinition = "INT(11) UNSIGNED")
    private int nroVisita;

    @ManyToOne
    @JoinColumn(name = "idPropiedad", nullable = false)
    private Propiedad id_propiedad; // Tu setter para esto es setIdPropiedad

    @Column (nullable = false, columnDefinition = "DATE") // Ajustado a DATE para LocalDate
    private LocalDate fecha;

    @Column (nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime hora_ini;

    @Column (nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime hora_fin;

    @Column (nullable = false, length = 45)
    private String nombre;

    @Column (nullable = true, length = 45)
    private String apellido;

    @Column (nullable = true, length = 45)
    private String telefono;

    // --- CAMPO NUEVO AÑADIDO ---
    @Column (nullable = true, length = 100) // Puedes ajustar el length
    private String email;
    
    
    // --- Getters (Tu código original) ---
    public int getNroVisita() {
        return nroVisita;
    }
    public Propiedad getIdPropiedad() { // Getter para la propiedad
        return id_propiedad;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public LocalDateTime getHoraIni() {
        return hora_ini;
    }
    public LocalDateTime getHoraFin() {
        return hora_fin;
    }
    public String getNombre() {
        return nombre;
    }
    public String getApellido() {
        return apellido;
    }
    // Tu getter de teléfono se llama getNumero(), está bien
    public String getNumero() { 
        return telefono;
    }
    
    // --- GETTER NUEVO ---
    public String getEmail() {
        return email;
    }
    
    
    // --- Setters (Tu código original) ---
    public void setNroVisita(int nroVisita) {
        this.nroVisita = nroVisita;
    }
    public void setIdPropiedad(Propiedad id_propiedad) {
        this.id_propiedad = id_propiedad;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public void setHoraIni(LocalDateTime hora_ini) {
        this.hora_ini = hora_ini;
    }
    public void setHoraFin(LocalDateTime hora_fin) {
        this.hora_fin = hora_fin;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // --- SETTER NUEVO ---
    public void setEmail(String email) {
        this.email = email;
    }
}