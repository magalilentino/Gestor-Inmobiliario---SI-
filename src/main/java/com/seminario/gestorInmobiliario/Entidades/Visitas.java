package com.seminario.gestorInmobiliario.Entidades;


import java.time.LocalDateTime;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "visitas")
public class Visitas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "nro_visita", nullable = false, unique = true, length = 11, columnDefinition = "INT(11) UNSIGNED")
    private int nroVisita;
    @Column (nullable = false, length = 10, columnDefinition = "DATE") 
    private LocalDateTime fechahora; 
    @Column (nullable = false, length = 45)
    private String nombre;
    @Column (nullable = true, length = 45)
    private String telefono;
    @Column (nullable = true, length = 45)
    private String email;
    
    public int getNroVisita() {
        return nroVisita;
    }
    public void setNroVisita(int nroVisita) {
        this.nroVisita = nroVisita;
    }
    public LocalDateTime getFechahora() {
        return fechahora;
    }
    public void setFechahora(LocalDateTime fechahora) {
        this.fechahora = fechahora;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Visitas(int nroVisita, LocalDateTime fechahora, String nombre, String telefono, String email) {
        this.nroVisita = nroVisita;
        this.fechahora = fechahora;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

}