package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "aumentos")
public class Aumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAumento; 
    @Column(unique = true, nullable = false)
    private int periodo;
    @Column(nullable = false)
    private double porcentajeAumento;

    //setters y getters 
    public int getIdAumento() {
        return idAumento;
    }
    public void setIdAumento(int idAumento) {
        this.idAumento = idAumento;
    }
    public int getPeriodo() {
        return periodo;
    }
    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }
    public double getPorcentajeAumento() {
        return porcentajeAumento;
    }
    public void setPorcentajeAumento(double porcentajeAumento) {
        this.porcentajeAumento = porcentajeAumento;
    }
    
}
