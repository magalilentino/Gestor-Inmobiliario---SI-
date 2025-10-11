package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agente")
public class Agente {
    @Id
    @Column(name = "dni_agente")
    private String dniAgente;

    @Column(name = "email", length = 45, unique = true, nullable = false)
    private String email;
    
    @Column(name = "nom_ape", length = 45, nullable = false)
    private String nomApe;

    @Column(name = "telefono", length = 45, unique = true, nullable = false)
    private String telefono;

    @Column(name = "matricula", length = 45, unique = true, nullable = false)
    private String matricula;

    @Column(name = "usuario", length = 45, unique = true, nullable = false)
    private String usuario;

    @Column(name = "clave", length = 45, nullable = false)
    private String clave;

    public String getDniAgente() {
        return dniAgente;
    }

    public void setDniAgente(String dniAgente) {
        this.dniAgente = dniAgente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    
    
}
