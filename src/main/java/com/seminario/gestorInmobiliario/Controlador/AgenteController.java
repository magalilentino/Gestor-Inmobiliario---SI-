package com.seminario.gestorInmobiliario.controller;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.service.AgenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agentes")
public class AgenteController {
    @Autowired
    private AgenteService agenteService;

    @PostMapping
    public ResponseEntity<Agente> crearAgente(@RequestBody Agente agente) {
        Agente nuevoAgente = agenteService.guardarAgente(agente);
        return ResponseEntity.ok(nuevoAgente);
    }

    @GetMapping
    public ResponseEntity<List<Agente>> listarAgentes() {
        List<Agente> agentes = agenteService.listarAgentes();
        return ResponseEntity.ok(agentes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agente> obtenerAgente(@PathVariable Long id) {
        Optional<Agente> agente = agenteService.buscarPorId(id);
        return agente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAgente(@PathVariable Long id) {
        agenteService.eliminarAgente(id);
        return ResponseEntity.noContent().build();
    }
}
