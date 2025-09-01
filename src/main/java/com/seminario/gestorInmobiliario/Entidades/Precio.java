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
    @Column(unique = true, nullable = false)
    private LocalDate mesDesde;
    @Column(unique = true, nullable = false)
    private LocalDate anioDesde;
    @Column(nullable = false)
    private double precio; 

    @ManyToOne
    @JoinColumn(name = "alquiler_id", nullable = false, unique = true)
    private Alquiler alquiler;

    
    public int getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(int idPrecio) {
        this.idPrecio = idPrecio;
    }

    public LocalDate getMesDesde() {
        return mesDesde;
    }

    public void setMesDesde(LocalDate mesDesde) {
        this.mesDesde = mesDesde;
    }

    public LocalDate getAnioDesde() {
        return anioDesde;
    }

    public void setAnioDesde(LocalDate anioDesde) {
        this.anioDesde = anioDesde;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    


}
