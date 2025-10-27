package com.seminario.gestorInmobiliario;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// import com.seminario.gestorInmobiliario.Servicios.AgenteServicios;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SeguridadWeb {

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

     @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize                       
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/").permitAll()
                        .requestMatchers("/login", "/registro", "/registrar").permitAll() 
                        .requestMatchers("/index").authenticated()
                        .anyRequest().authenticated()  
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("usuario")
                        .passwordParameter("clave")
                        .successHandler(loginSuccessHandler)
                        // .defaultSuccessUrl("/index", true)
                        .permitAll())    
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public AuthenticationSuccessHandler successHandler() {
    //     return (request, response, authentication) -> {
    //         String email = authentication.getName();
    //         Persona persona = personaRepository.buscarPorEmail(email);
    //         request.getSession().setAttribute("personaSession", persona);
    //         response.sendRedirect("/"); 
    //     };
    // }


}
