package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agentes_inmobiliarios")
public class Agente {
    @Id
    private String dniAgente;

    @Column(length = 45, unique = true, nullable = false)
    private String email;
    
    @Column(length = 45, nullable = false)
    private String nomApe;

    @Column(length = 45, unique = true, nullable = false)
    private String telefono;

    @Column(length = 45, unique = true, nullable = false)
    private String matricula;

    @Column(length = 45, unique = true, nullable = false)
    private String usuario;

    @Column(length = 45, nullable = false)
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
