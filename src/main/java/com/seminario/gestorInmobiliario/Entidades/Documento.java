package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "documento")
public class Documento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_documento")
    private int idDocumento;

    @Column(name = "enlace", nullable = false, length = 50)
    private String enlace;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenido;

    // Constructor vacío
    public Documento() {}

    // Constructor con parámetros
    public Documento(String enlace, String descripcion, byte[] contenido) {
        this.enlace = enlace;
        this.descripcion = descripcion;
        this.contenido = contenido;
    }

    // Getters y Setters
    public int getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getEnlace() {
        return enlace;
    }

    public void setEnlace(String enlace) {
        this.enlace = enlace;
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

    @Override
    public String toString() {
        return "Documento{" +
                "idDocumento=" + idDocumento +
                ", enlace='" + enlace + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
