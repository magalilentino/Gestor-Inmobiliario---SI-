package com.seminario.gestorInmobiliario.Servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.seminario.gestorInmobiliario.Entidades.Agente;
import com.seminario.gestorInmobiliario.Repositorios.AgenteRepository;

@Service
public class AgenteServicios implements UserDetailsService {

    @Autowired
    private AgenteRepository agenteRepositorio;

    @Transactional // Todos los metodos que generen cambios en la base de dados
    public void crearAgente(String dniAgente, String nomApe, String email, String telefono, String matricula, String usuario, String clave)
            throws Exception {

        validar(dniAgente, email, matricula, usuario, clave);
        Agente agente = new Agente();

        agente.setDniAgente(dniAgente);
        agente.setNomApe(nomApe);
        agente.setEmail(email);
        agente.setTelefono(telefono);
        agente.setMatricula(matricula);
        agente.setUsuario(usuario);
        agente.setClave(new BCryptPasswordEncoder().encode(clave));
        agenteRepositorio.save(agente);
    }

    @Transactional(readOnly = true)
    public List<Agente> listarAgentes() {
        return agenteRepositorio.findAll();  
    }

    @Transactional
    public void modificarAgente(String dniAgente, String nomApe, String email, String telefono, String matricula, String usuario, String clave)
            throws Exception {

        validar(dniAgente, email, matricula, usuario, clave);
        Optional<Agente> respuesta = agenteRepositorio.findById(dniAgente);
        if (respuesta.isPresent()) {
            Agente agente = respuesta.get();
            agente.setDniAgente(dniAgente);
            agente.setNomApe(nomApe);
            agente.setEmail(email);
            agente.setTelefono(telefono);
            agente.setMatricula(matricula);
            agente.setUsuario(usuario);
            agente.setClave(new BCryptPasswordEncoder().encode(clave));
            agenteRepositorio.save(agente);
        } else {
            throw new Exception("No se encontro el agente solicitado");
        }
    }

    @Transactional
    public void eliminarAgente(String dniAgente) throws Exception {
        Optional<Agente> respuesta = agenteRepositorio.findById(dniAgente);
        if (respuesta.isPresent()) {
            Agente agente = respuesta.get();
            agenteRepositorio.delete(agente);
        } else {
            throw new Exception("No se encontro el agente solicitado");
        }

    }

    @Transactional(readOnly = true)
    public Agente getOne(String dniAgente) {
        return agenteRepositorio.getReferenceById(dniAgente);
    }

    @Transactional(readOnly = true)
    public Agente getByUser(String usuario) {
        return agenteRepositorio.findByUsuario(usuario).orElse(null);
    }

    // public Agente login(String usuario, String clave) throws Exception {
    //     Agente agente = agenteRepositorio.findByUsuario(usuario)
    //             .orElseThrow(() -> new Exception("Usuario no encontrado"));

    //     if (!agente.getClave().equals(clave)) {
    //         throw new Exception("Clave incorrecta");
    //     }

    //     return agente;
    // }

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        Agente agente = agenteRepositorio.findByUsuario(usuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return User.builder()
                .username(agente.getUsuario())
                .password(agente.getClave()) 
                .build();
    }


    private void validar(String dniAgente, String email, String matricula, String usuario, String clave) throws Exception {

        if (dniAgente == null || dniAgente.isEmpty()) {
            throw new Exception("El DNI del agente no puede ser nulo o estar vacio");
        }

        if (email == null || email.isEmpty()) {
            throw new Exception("El email del agente no puede ser nulo o estar vacio");
        }

        if (matricula == null || matricula.isEmpty()) {
            throw new Exception("La matricula del agente no puede ser nulo o estar vacio");
        }

        if (usuario == null || usuario.isEmpty()) {
            throw new Exception("El usuario del agente no puede ser nulo o estar vacio");
        }

        if (clave == null || clave.isEmpty()) {
            throw new Exception("La clave del agente no puede ser nulo o estar vacio");
        }

        if (agenteRepositorio.findByUsuario(usuario).isPresent()) {
            throw new Exception("El usuario ya existe");
        }
    }
}
