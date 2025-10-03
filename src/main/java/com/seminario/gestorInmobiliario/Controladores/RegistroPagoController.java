package com.seminario.gestorInmobiliario.Controladores;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Servicios.InquilinoService;
import com.seminario.gestorInmobiliario.Servicios.PagoService;

import ch.qos.logback.core.model.Model;


import com.seminario.gestorInmobiliario.Entidades.Alquiler;
import com.seminario.gestorInmobiliario.Entidades.Documento;
import com.seminario.gestorInmobiliario.Entidades.Pago;
import com.seminario.gestorInmobiliario.Servicios.AlquilerServicio;
import com.seminario.gestorInmobiliario.Servicios.DocumentoServicio;
import com.seminario.gestorInmobiliario.Servicios.FormaPagoServicio;
import com.seminario.gestorInmobiliario.Repositorios.PagoRepository;


@Controller
@RequestMapping("/registro-pago")
public class RegistroPagoController {

    @Autowired
    private InquilinoService inquilinoService;

    @Autowired
    private AlquilerServicio alquilerService;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private FormaPagoServicio formaPagoService;

    @Autowired
    private DocumentoServicio documentoService;

    @Autowired
    private PagoRepository pagoRepository;

    // Una ruta para la carga inicial
    @GetMapping("/carga-inicial") 
    public String mostrarFormulario() {
        return "pago/alquileresActivos"; // Muestra la vista con el formulario vacío
}

    // Paso 1: Buscar alquileres activos por DNI
    @PostMapping("/buscar-inquilino")
    @Transactional //para garantizar que la bd se mantenga abierta hasta que la vista haya terminado de acceder a las propiedades 
    public String buscarInquilino(@RequestParam String dni, ModelMap model) {
        try {
            List<Alquiler> alquileres = inquilinoService.buscarAlquileresActivosPorDni(dni);
            model.addAttribute("alquileres", alquileres);
            return "pago/alquileresActivos"; // vista para mostrar alquileres activos
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pago/alquileresActivos"; // vuelve al paso 1
        }
    }


    // Paso 2: Mostrar pagos pendientes del alquiler seleccionado
    @GetMapping("/pagos-pendientes")
    public String mostrarPagosPendientes(@RequestParam int idAlquiler, ModelMap model) {
        try {
            List<Pago> pagos = pagoRepository.findPagosPendientesPorAlquiler(idAlquiler, "pendiente");

            if (pagos.isEmpty()) {
                model.addAttribute("mensaje", "El alquiler no tiene pagos pendientes.");
                return "pago/pagosPendientes"; 
            }
            
            model.addAttribute("pagos", pagos);
            model.addAttribute("idAlquiler", idAlquiler);
            return "pago/pagosPendientes";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error interno al buscar pagos: " + e.getMessage());
            return "pago/alquileresActivos"; 
        }
}

    // Paso 3: Calcular monto directamente y mostrar resultado
    @PostMapping("/calcular-monto")
    public String calcularMonto(@RequestParam int idPago,
                                @RequestParam int idAlquiler,
                                @RequestParam String fechaPago,
                                ModelMap model) {
        try {
            LocalDate fecha = LocalDate.parse(fechaPago);
            Pago pago = pagoRepository.findById(idPago)
                .orElseThrow(() -> new Exception("Pago no encontrado"));

            double montoBase = alquilerService.getPrecioActual();
            int diasRetraso = 0;

            if (fecha.isAfter(pago.getFecha_limite())) {
                diasRetraso = (int) ChronoUnit.DAYS.between(pago.getFecha_limite(), fecha);
                double interes = pago.getInteresMora() * diasRetraso;
                montoBase += interes;
                model.addAttribute("mensajeRetraso", "El pago tiene " + diasRetraso + " días de retraso.");
            }
            pago.setFechaPago(fecha);
            model.addAttribute("pago", pago);
            model.addAttribute("formasPago", formaPagoService.listarFormasPago());
            model.addAttribute("monto", montoBase);
            return "pago/confirmarPago";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("idAlquiler", idAlquiler);
            return "pago/pagosPendientes";
        }
    }


    // Paso 4: Registrar pago y opcionalmente generar comprobante
    @PostMapping("/registrar")
    @Transactional
    public String registrarPago(@RequestParam int idPago,
                                @RequestParam int idFormaPago,
                                @RequestParam(required = false) boolean solicitarComprobante,
                                ModelMap model) {
        try {
            Pago pago = pagoService.registrarComoPagado(idPago, idFormaPago);
            model.addAttribute("mensaje", "Pago registrado correctamente.");

            if (solicitarComprobante) {
                model.addAttribute("pago", pago);
                return "redirect:/registro-pago/mostrar-comprobante?idPago=" + pago.getIdPago();  
            }

            return "pago/registrarPago"; // vista simple con mensaje
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "pago/registrarPago";
        }
    }

    // CA4 - se solicita el comprobante 
    @GetMapping("/mostrar-comprobante")
    public String mostrarComprobante(@RequestParam int idPago, ModelMap model) {
        try {
            Pago pago = pagoRepository.findById(idPago).orElse(null);
            model.addAttribute("pago", pago);
            Alquiler alquiler = pago.getAlquiler();
            model.addAttribute("monto", alquiler.getPrecio(pago.getFechaPago()));
            return "pago/mostrarComprobante";
        } catch (Exception e) {
            model.addAttribute("error", "Error al cargar comprobante: ");
            return "pago/registrarPago"; // Vuelve a una vista segura
    }
}}