package com.seminario.gestorInmobiliario.Repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< Updated upstream

public interface AgenteRepository extends JpaRepository<Agente, Long> {
}
=======
import org.springframework.stereotype.Repository;

import com.seminario.gestorInmobiliario.Entidades.Agente;

@Repository
public interface AgenteRepository extends JpaRepository<Agente, String> {

    // Spring Data JPA creará automáticamente este método
    // basado en el nombre del campo 'usuario' en la entidad Agente
    Optional<Agente> findByUsuario(String usuario);
    
    // Opcional: también puedes verificar si existe un usuario
    boolean existsByUsuario(String usuario);
    
    // Opcional: buscar por email
    Optional<Agente> findByEmail(String email);
}
>>>>>>> Stashed changes
