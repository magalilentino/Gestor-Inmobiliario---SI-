package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "localidades")
public class Localidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idLocalidad; 
    private String nombre;
    private int codPostal;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia miProvincia;

    //getters y setters 
    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public Provincia getMiProvincia() {
        return miProvincia;
    }

    public void setMiProvincia(Provincia miProvincia) {
        this.miProvincia = miProvincia;
    } 
    
}
