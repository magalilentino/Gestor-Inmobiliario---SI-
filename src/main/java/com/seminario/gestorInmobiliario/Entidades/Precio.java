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
@Table(name = "precios")
public class Precio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPrecio; 
    @Column( nullable = false)
    private LocalDate fechaDesde;
    @Column(nullable = false)
    private double montoPrecio; 

    @ManyToOne
    @JoinColumn(name = "alquiler_id", nullable = false)
    private Alquiler alquiler;

    
    public int getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(int idPrecio) {
        this.idPrecio = idPrecio;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public double getPrecio() {
        return montoPrecio;
    }

    public void setPrecio(double montoPrecio) {
        this.montoPrecio =montoPrecio;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    


}
