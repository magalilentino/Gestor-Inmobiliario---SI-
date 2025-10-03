package com.seminario.gestorInmobiliario.Entidades;

import java.io.Serializable;

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
@Table(name = "documento")
public class Documento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private int idDocumento;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenido;

    @ManyToOne
    @JoinColumn(name = "id_alquiler")
    private Alquiler alquiler;

    @ManyToOne
    @JoinColumn(name = "idPago", nullable = false)
    private Pago pago;

    // Constructor vacío
    public Documento() {}

    // Constructor con parámetros
    public Documento(String descripcion, byte[] contenido, Alquiler alquiler) {
        this.descripcion = descripcion;
        this.contenido = contenido;
        this.alquiler = alquiler;
    }

    // Getters y Setters
    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }

    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "idDocumento=" + idDocumento +
                ", descripcion='" + descripcion + '\'' +
                ", contenido='" + (contenido != null ? "[CONTENIDO BINARIO]" : "null") + '\'' +
                ", alquiler=" + (alquiler != null ? alquiler.getIdAlquiler() : "null") +
                '}';
    }
}
