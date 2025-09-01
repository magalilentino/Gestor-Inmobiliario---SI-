package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "propiedades")

public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 
    private String ubicacion;
    private String medidas; 
    private int cantAmbientes; 
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] contenido;


}