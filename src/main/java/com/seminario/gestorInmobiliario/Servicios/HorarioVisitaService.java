package com.seminario.gestorInmobiliario.Servicios;
import java.time.Duration;
import java.time.LocalTime;

import org.springframework.stereotype.Service;

import com.seminario.gestorInmobiliario.Entidades.HorarioVisita;
import com.seminario.gestorInmobiliario.Repositorios.HorarioVisitaRepository;

@Service
public class HorarioVisitaService {

    private final HorarioVisitaRepository horarioVisitaRepository;

    public HorarioVisitaService(HorarioVisitaRepository horarioVisitaRepository) {
        this.horarioVisitaRepository = horarioVisitaRepository;
    }

    public void guardarHorario(HorarioVisita horario) {
        LocalTime inicio = horario.getHoraIni();
        LocalTime fin = horario.getHoraFin();

        // Validar que la hora de fin sea posterior a la de inicio
        if (!fin.isAfter(inicio)) {
            throw new IllegalArgumentException("La hora de fin debe ser posterior a la hora de inicio.");
        }

        // Validar duración exacta de 30 minutos
        if (!Duration.between(inicio, fin).equals(Duration.ofMinutes(30))) {
            throw new IllegalArgumentException("La duración debe ser exactamente de 30 minutos.");
        }

        // Validar que las horas sean en punto o y media
        if (inicio.getMinute() != 0 && inicio.getMinute() != 30) {
            throw new IllegalArgumentException("La hora de inicio debe ser en punto o y media.");
        }

        if (fin.getMinute() != 0 && fin.getMinute() != 30) {
            throw new IllegalArgumentException("La hora de fin debe ser en punto o y media.");
        }

        horarioVisitaRepository.save(horario);
    }
}
