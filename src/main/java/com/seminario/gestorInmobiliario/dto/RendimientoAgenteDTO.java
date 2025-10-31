package com.seminario.gestorInmobiliario.dto;

public class RendimientoAgenteDTO {
    private String nombre;
    private long totalAlquileres;
    private double totalIngresos;
    
    public RendimientoAgenteDTO(String nombre, long totalAlquileres, double totalIngresos) {
        this.nombre = nombre;
        this.totalAlquileres = totalAlquileres;
        this.totalIngresos = totalIngresos;
    }
    // Getters
    public String getNombre() { return nombre; }
    public long getTotalAlquileres() { return totalAlquileres; }
    public double getTotalIngresos() { return totalIngresos; }
}