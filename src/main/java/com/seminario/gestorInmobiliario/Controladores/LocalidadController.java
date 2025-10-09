package com.seminario.gestorInmobiliario.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.seminario.gestorInmobiliario.Entidades.Localidad;
import com.seminario.gestorInmobiliario.Servicios.LocalidadServicio;

@Controller
@RequestMapping("/localidad")
public class LocalidadController {
    @Autowired
    private LocalidadServicio localidadService;

    @GetMapping("/listaProv")
    @ResponseBody
    public List<Localidad> obtenerLocalidadesPorProvincia(@RequestParam int idProvincia) {
        return localidadService.buscarPorProvincia(idProvincia);
    }


}