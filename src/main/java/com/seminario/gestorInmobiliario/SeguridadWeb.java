package com.particulares.tp.java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.particulares.tp.java.entities.Persona;
import com.particulares.tp.java.repository.PersonaRepository;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebMvc
public class SeguridadWeb {

    @Autowired
    private PersonaRepository personaRepository;

     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize                       
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/").permitAll()
                        .requestMatchers("/login", "/registro", "/registrar").permitAll() 
                        .requestMatchers("/localidad/listaProv").permitAll()
                        .anyRequest().authenticated()  
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("clave")
                        .defaultSuccessUrl("/inicio", true)
                        .permitAll())    
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            String email = authentication.getName();
            Persona persona = personaRepository.buscarPorEmail(email);
            request.getSession().setAttribute("personaSession", persona);
            response.sendRedirect("/"); 
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
