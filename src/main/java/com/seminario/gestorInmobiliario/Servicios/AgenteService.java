package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Repositorios.AgenteRepository;

@Service
public class AgenteService {

    @Autowired
    private AgenteRepository agenteRepositorio;

    // Crear o actualizar un agente
    public Agente guardarAgente(Agente agente) {
        return agenteRepositorio.save(agente);
    }

    // Listar todos los agentes
    public List<Agente> listarAgentes() {
        return agenteRepositorio.findAll();
    }

    // Buscar por ID
    public Optional<Agente> buscarPorId(Long id) {
        return agenteRepositorio.findById(id);
    }

    // Eliminar por ID
    public void eliminarAgente(Long id) {
        agenteRepositorio.deleteById(id);
    }
}
