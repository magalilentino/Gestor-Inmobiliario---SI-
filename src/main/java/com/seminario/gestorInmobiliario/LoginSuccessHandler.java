package com.seminario.gestorInmobiliario;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Repositorios.AgenteRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private AgenteRepository agenteRepositorio;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String usuario = authentication.getName();
        Agente agente = agenteRepositorio.findByUsuario(usuario).orElse(null);

        if (agente != null) {
            request.getSession().setAttribute("agentesession", agente);
        }

        response.sendRedirect("/index");
    }
}
