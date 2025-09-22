package com.seminario.gestorInmobiliario.Entidades;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "alquileres")
public class Alquiler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlquiler;

    @Column(unique = true, nullable = false)
    private LocalDate fechaIngreso;
    @Column(unique = true, nullable = false)
    private LocalDate fechaEgreso;
    private LocalDate fechaRecision;
    @Column(unique = true, nullable = false)
    private double valorInicial;

    @ManyToOne
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedad miPropiedad;

    @OneToMany
    @JoinColumn(name = "alquiler_id", nullable = false)  // Nombre diferente
    private List<Pago> misPagos;

    @ManyToOne
    @JoinColumn(name = "inquilino_id", nullable = false)
    private Inquilino miInquilino;

    @ManyToOne
    @JoinColumn(name = "agente_id", nullable = false)
    private Agente miAgente;

    @ManyToOne
    @JoinColumn(name = "aumento_id")
    private Aumento miAumento;

    @OneToMany(mappedBy = "alquiler", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();

    //setters y getters 
    public int getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(int idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDate getFechaEgreso() {
        return fechaEgreso;
    }

    public void setFechaEgreso(LocalDate fechaEgreso) {
        this.fechaEgreso = fechaEgreso;
    }

    public LocalDate getFechaRecision() {
        return fechaRecision;
    }

    public void setFechaRecision(LocalDate fechaRecision) {
        this.fechaRecision = fechaRecision;
    }

    public double getValorInicial() {
        return valorInicial;
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }

    public Propiedad getMiPropiedad() {
        return miPropiedad;
    }

    public void setMiPropiedad(Propiedad miPropiedad) {
        this.miPropiedad = miPropiedad;
    }

    public List<Pago> getMisPagos() {
        return misPagos;
    }

    public void setMisPagos(List<Pago> misPagos) {
        this.misPagos = misPagos;
    }

    public Inquilino getMiInquilino() {
        return miInquilino;
    }

    public void setMiInquilino(Inquilino miInquilino) {
        this.miInquilino = miInquilino;
    }

    public Agente getMiAgente() {
        return miAgente;
    }

    public void setMiAgente(Agente miAgente) {
        this.miAgente = miAgente;
    }

    public Aumento getMiAumento() {
        return miAumento;
    }

    public void setMiAumento(Aumento miAumento) {
        this.miAumento = miAumento;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }




}
