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

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] archivo;

    // Constructor vacío
    public Documento() {}

    // Constructor con parámetros
    public Documento(String descripcion, byte[] archivo) {
        this.descripcion = descripcion;
        this.archivo = archivo;
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

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "idDocumento=" + idDocumento +
                ", archivo='" + archivo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
