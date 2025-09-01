package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "agentes_inmobiliarios")
public class Agente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    
}
