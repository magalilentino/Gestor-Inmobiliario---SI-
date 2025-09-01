package com.seminario.gestorInmobiliario.Entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alquileres")
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlquiler; 

    @Column( unique = true, nullable = false)
    private LocalDate fechaIngreso;
    @Column( unique = true, nullable = false)
    private LocalDate fechaEgreso;
    private LocalDate fechaRecision;
    @Column(unique = true, nullable = false)
    private double valorInicial;

    @ManyToOne
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedad miPropiedad;

    //setters y getters 
    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public LocalDate getFechaRecision() {
        return fechaRecision;
    }

    public void setFechaRecision(LocalDate fechaRecision) {
        this.fechaRecision = fechaRecision;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public Propiedad getMiPropiedad() {
        return miPropiedad;
    }

    public void setMiPropiedad(Propiedad miPropiedad) {
        this.miPropiedad = miPropiedad;
    }

    @ManyToOne
    @JoinColumn(name = "inquilino_id", nullable = false)
    private Inquilino miInquilino;
    
    @ManyToOne
    @JoinColumn(name = "agente_id", nullable = false)
    private Agente miAgente;
    
    @ManyToOne
    @JoinColumn(name = "aumento_id")
    private Aumento miAumento;

    

}
