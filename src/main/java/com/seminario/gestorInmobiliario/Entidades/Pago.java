package com.seminario.gestorInmobiliario.Entidades;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPago;

    @Column(nullable = false)
    private LocalDate fecha_limite;

    @Column(nullable = false)
    private String estado;

    private LocalDate fechaPago;

    @Column(nullable = true)
    private Double montoPagado;

    @ManyToOne
    @JoinColumn(name = "idFormaPago")
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "alquiler_id", nullable = false)
    private Alquiler alquiler;
    


    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }
    
    public Alquiler getAlquiler() {
        return alquiler;
    }

    public void setAlquiler(Alquiler alquiler) {
        this.alquiler = alquiler;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public LocalDate getFecha_limite() {
        return fecha_limite;
    }

    public void setFecha_limite(LocalDate fecha_limite) {
        this.fecha_limite = fecha_limite;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public String getEstado(){
        return estado;
    }
    public void setEstado(){
        this.estado= "pendiente";
    }

    //cambiar estado 
    public void  cambiarEstado() {
        this.estado = "Pagado";
    }
    
}
