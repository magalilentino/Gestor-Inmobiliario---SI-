package com.seminario.gestorInmobiliario.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "visitas_propiedades")
public class VisitaPropiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idVisitaProp;

    @ManyToOne
<<<<<<< HEAD
    @JoinColumn(name = "id_visita", nullable = false)
    private Visitas visita;

    @ManyToOne
    @JoinColumn(name = "id_propiedad", nullable = false)
=======
    @JoinColumn(name = "idVisita", nullable = false)
    private Visitas visita;

    @ManyToOne
    @JoinColumn(name = "idPropiedad", nullable = false)
>>>>>>> origin/rama-deque
    private Propiedad propiedad;

    public int getIdVisitaProp() {
        return idVisitaProp;
    }

    public void setIdVisitaProp(int idVisitaProp) {
        this.idVisitaProp = idVisitaProp;
    }

    public Visitas getVisita() {
        return visita;
    }

    public void setVisita(Visitas visita) {
        this.visita = visita;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    
    
}