package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "propiedades")

public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPropiedad; 
    @Column(nullable = false)
    private String ubicacion;
    @Column(nullable = false)
    private String medidas; 
    @Column(nullable = false)
    private int cantAmbientes; 

    @ManyToOne
    @JoinColumn(name = "localidad_id", nullable = false)
    private Localidad miLocalidad;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria miCategoria;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenido;

    //getters y setters 
    public int getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(int idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public int getCantAmbientes() {
        return cantAmbientes;
    }

    public void setCantAmbientes(int cantAmbientes) {
        this.cantAmbientes = cantAmbientes;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public Localidad getLocalidad() {
        return miLocalidad;
    }

    public void setLocalidad(Localidad miLocalidad) {
        this.miLocalidad = miLocalidad;
    }

    public Categoria getCategoria() {
        return miCategoria;
    }

    public void setCategoria(Categoria miCategoria) {
        this.miCategoria = miCategoria;
    }

}