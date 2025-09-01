package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "provincia")

public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private String nombre;

    //setters y getters 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
