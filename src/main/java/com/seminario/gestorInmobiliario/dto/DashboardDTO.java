package com.seminario.gestorInmobiliario.dto;

import java.util.List;

public class DashboardDTO {
    // KPIs Principales
    private double ingresosActivos;
    private long propiedadesDisponibles;
    private long propiedadesAlquiladas;
    private long visitasEsteMes;
    private long alquileresNuevosEsteMes;
    private double tasaConversion;

    // Datos para Gráficos
    private List<ConteoPorEstadoDTO> conteoPorEstado;
    private List<RendimientoAgenteDTO> rendimientoAgentes;
    private List<TopPropiedadDTO> topPropiedadesVisitadas;

    // --- Getters y Setters ---

    public double getIngresosActivos() {
        return ingresosActivos;
    }

    public void setIngresosActivos(double ingresosActivos) {
        this.ingresosActivos = ingresosActivos;
    }

    public long getPropiedadesDisponibles() {
        return propiedadesDisponibles;
    }

    public void setPropiedadesDisponibles(long propiedadesDisponibles) {
        this.propiedadesDisponibles = propiedadesDisponibles;
    }

    public long getPropiedadesAlquiladas() {
        return propiedadesAlquiladas;
    }

    public void setPropiedadesAlquiladas(long propiedadesAlquiladas) {
        this.propiedadesAlquiladas = propiedadesAlquiladas;
    }

    public long getVisitasEsteMes() {
        return visitasEsteMes;
    }

    public void setVisitasEsteMes(long visitasEsteMes) {
        this.visitasEsteMes = visitasEsteMes;
    }

    public long getAlquileresNuevosEsteMes() {
        return alquileresNuevosEsteMes;
    }

    public void setAlquileresNuevosEsteMes(long alquileresNuevosEsteMes) {
        this.alquileresNuevosEsteMes = alquileresNuevosEsteMes;
    }

    public double getTasaConversion() {
        return tasaConversion;
    }

    public void setTasaConversion(double tasaConversion) {
        this.tasaConversion = tasaConversion;
    }

    public List<ConteoPorEstadoDTO> getConteoPorEstado() {
        return conteoPorEstado;
    }

    public void setConteoPorEstado(List<ConteoPorEstadoDTO> conteoPorEstado) {
        this.conteoPorEstado = conteoPorEstado;
    }

    public List<RendimientoAgenteDTO> getRendimientoAgentes() {
        return rendimientoAgentes;
    }

    public void setRendimientoAgentes(List<RendimientoAgenteDTO> rendimientoAgentes) {
        this.rendimientoAgentes = rendimientoAgentes;
    }

    public List<TopPropiedadDTO> getTopPropiedadesVisitadas() {
        return topPropiedadesVisitadas;
    }

    public void setTopPropiedadesVisitadas(List<TopPropiedadDTO> topPropiedadesVisitadas) {
        this.topPropiedadesVisitadas = topPropiedadesVisitadas;
    }
}