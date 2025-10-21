package com.seminario.gestorInmobiliario;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class SchedulerConfig {
    // No necesita contenido, solo habilita el scheduling

// - Los métodos anotados con @Scheduled se ejecutan automáticamente en segundo plano cuando arranca la aplicación.
// - No dependen de una petición HTTP ni de una acción del usuario.
// - No necesitan estar expuestos en un @Controller ni en una URL.

}
