package com.seminario.gestorInmobiliario.dto;

public class TopPropiedadDTO {
    private String ubicacion;
    private long cantidadVisitas;
    
    public TopPropiedadDTO(String ubicacion, long cantidadVisitas) {
        this.ubicacion = ubicacion;
        this.cantidadVisitas = cantidadVisitas;
    }
    // Getters
    public String getUbicacion() { return ubicacion; }
    public long getCantidadVisitas() { return cantidadVisitas; }
}