package com.seminario.gestorInmobiliario.Servicios;

import com.seminario.gestorInmobiliario.Repositorios.AlquilerRepository;
import com.seminario.gestorInmobiliario.Repositorios.PropiedadRepository;
import com.seminario.gestorInmobiliario.Repositorios.VisitasRepository;
import com.seminario.gestorInmobiliario.dto.ConteoPorEstadoDTO;
import com.seminario.gestorInmobiliario.dto.DashboardDTO;
import com.seminario.gestorInmobiliario.dto.RendimientoAgenteDTO;
import com.seminario.gestorInmobiliario.dto.TopPropiedadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    @Autowired
    private PropiedadRepository propiedadRepo;
    @Autowired
    private AlquilerRepository alquilerRepo;
    @Autowired
    private VisitasRepository visitasRepo;

    public DashboardDTO getDashboardInfo() {
        DashboardDTO dto = new DashboardDTO();

        // 1. Definir rango de fechas (este mes)
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        // 2. Cargar KPIs Principales
        dto.setIngresosActivos(alquilerRepo.sumarIngresosActivos() != null ? alquilerRepo.sumarIngresosActivos() : 0.0);
        dto.setPropiedadesDisponibles(propiedadRepo.contarPorEstado("Disponible"));
        dto.setPropiedadesAlquiladas(propiedadRepo.contarPorEstado("Alquilada"));
        dto.setVisitasEsteMes(visitasRepo.contarVisitasEnRango(inicioMes, finMes));
        dto.setAlquileresNuevosEsteMes(alquilerRepo.contarAlquileresNuevosEnRango(inicioMes, finMes));
        
        // 3. Calcular Tasa de Conversión
        if (dto.getVisitasEsteMes() > 0) {
            dto.setTasaConversion((double) dto.getAlquileresNuevosEsteMes() / dto.getVisitasEsteMes() * 100);
        } else {
            dto.setTasaConversion(0.0);
        }

        // 4. Cargar datos para Gráficos (convirtiendo List<Object[]> a List<DTO>)
        dto.setConteoPorEstado(
            propiedadRepo.contarTotalPorEstado().stream()
                .map(obj -> new ConteoPorEstadoDTO((String) obj[0], (long) obj[1]))
                .collect(Collectors.toList())
        );

        dto.setRendimientoAgentes(
            alquilerRepo.findRendimientoAgentesActivos().stream()
                .map(obj -> new RendimientoAgenteDTO((String) obj[0], (long) obj[1], (double) obj[2]))
                .collect(Collectors.toList())
        );
        
        dto.setTopPropiedadesVisitadas(
            visitasRepo.findTopPropiedadesVisitadasEnRango(inicioMes, finMes).stream()
                .map(obj -> new TopPropiedadDTO((String) obj[0], (long) obj[1]))
                .limit(5) // Nos aseguramos de tomar solo el Top 5
                .collect(Collectors.toList())
        );
        
        return dto;
    }
}