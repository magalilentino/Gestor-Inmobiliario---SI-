package com.seminario.gestorInmobiliario.dto;

public class ConteoPorEstadoDTO {
    private String estado;
    private long cantidad;
    
    public ConteoPorEstadoDTO(String estado, long cantidad) {
        this.estado = estado;
        this.cantidad = cantidad;
    }
    // Getters
    public String getEstado() { return estado; }
    public long getCantidad() { return cantidad; }
}