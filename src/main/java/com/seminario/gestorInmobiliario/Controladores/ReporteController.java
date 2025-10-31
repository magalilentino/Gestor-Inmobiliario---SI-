package com.seminario.gestorInmobiliario.Controladores;

import com.seminario.gestorInmobiliario.Servicios.ReporteService;
import com.seminario.gestorInmobiliario.dto.DashboardDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reportes") // La URL base será /reportes
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public String mostrarDashboard(Model model) {
        DashboardDTO dashboardData = reporteService.getDashboardInfo();
        model.addAttribute("dashboard", dashboardData);
        return "reportes/dashboard"; // Apunta al archivo HTML
    }
}