package com.seminario.gestorInmobiliario.Entidades;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

    @Column( nullable = false)
    private LocalDate fechaIngreso;
    @Column( nullable = false)
    private LocalDate fechaEgreso;
    private LocalDate fechaRecision;
    @Column(nullable = false)
    private double valorInicial;
    @Column(nullable = false)
    private String estado;
    @Column(nullable = false)
    private int periodoAumento;
    @Column(nullable = false)
    private double porcentajeAumento;
    @Column(nullable = false)
    private double interesMora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedad miPropiedad;

    @OneToMany(mappedBy = "alquiler")
    //@JoinColumn(name = "alquiler_id", nullable = false)
    private List<Pago> misPagos;

    @OneToMany(mappedBy = "alquiler")
    //@JoinColumn(name = "alquiler_id", nullable = false)
    private List<Precio> misPrecios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquilino_id", nullable = false)
    private Inquilino miInquilino;

    @ManyToOne
    @JoinColumn(name = "dniAgente", nullable = false)
    private Agente miAgente;

    // @ManyToOne
    // @JoinColumn(name = "aumento_id")
    // private Aumento miAumento;

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

    // public Aumento getMiAumento() {
    //     return miAumento;
    // }

    // public void setMiAumento(Aumento miAumento) {
    //     this.miAumento = miAumento;
    // }

    public List<Precio> getMisPrecios() {
        return misPrecios;
    }

    public void setMisPrecios(List<Precio> misPrecios) {
        this.misPrecios = misPrecios;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getPeriodoAumento() {
        return periodoAumento;
    }

    public void setPeriodoAumento(int periodoAumento) {
        this.periodoAumento = periodoAumento;
    }

    public double getPorcentajeAumento() {
        return porcentajeAumento;
    }

    public void setPorcentajeAumento(double porcentajeAumento) {
        this.porcentajeAumento = porcentajeAumento;
    }

    public double getInteresMora() {
        return interesMora;
    }

    public void setInteresMora(double interesMora) {
        this.interesMora = interesMora;
    }


    //calculo el total 
    public double getPrecio(LocalDate fechaPago){
        double precio=0;
        for(Precio p: misPrecios){
            if(!p.getFechaDesde().isAfter(fechaPago)){
                precio= p.getPrecio();
            }
        }
        return precio;
    }

    public int getMesesDesdeIngreso() {
        LocalDate hoy = LocalDate.now();
        return Period.between(fechaIngreso.withDayOfMonth(1), hoy.withDayOfMonth(1)).getMonths()
            + Period.between(fechaIngreso.withDayOfMonth(1), hoy.withDayOfMonth(1)).getYears() * 12;
    }

}
