package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inquilinos")
public class Inquilino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String dniInquilino;
    
    @Column(length = 45)
    private String nomApe;

    @Column(length = 45, unique = true)
    private String telefono;

    @Column(length = 45, unique = true)
    private String email;


    public String getDniInquilino() {
        return dniInquilino;
    }

    public void setDniInquilino(String dni_inquilino) {
        this.dniInquilino = dni_inquilino;
    }

    public String getNomApe() {
        return nomApe;
    }

    public void setNomApe(String nomApe) {
        this.nomApe = nomApe;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
