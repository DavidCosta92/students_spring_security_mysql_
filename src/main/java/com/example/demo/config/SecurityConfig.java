package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationProvider authProvider;
    // private final AuthenticationManager authenticationManager;

    // OPCION 1 DE CONFIG
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // .authorizeHttpRequests(auth -> {
                // auth.requestMatchers("/auth/**").permitAll();
                // auth.anyRequest().authenticated();
                // })
                // .formLogin(login -> login
                // .successHandler(successHandler()).permitAll())
                // .sessionManagement(management -> management
                // .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                // .invalidSessionUrl("/auth/login")
                // .maximumSessions(1) // podrian ser mas si es multiplataforma cel y notebook
                // por ej
                // .expiredUrl("/auth/login")
                // .sessionRegistry(sessionRegistry()) // para rastrear los datos del user
                // authenticado
                // )
                // .build();
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(login -> login
                        .successHandler(successHandler()).permitAll())
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                        .invalidSessionUrl("/auth/login")
                        .maximumSessions(1) // podrian ser mas si es multiplataforma cel y notebook por ej
                        .expiredUrl("/auth/login")
                        .sessionRegistry(sessionRegistry()) // para rastrear los datos del user authenticado
                )
                .authenticationProvider(authProvider)
                // .addFilter(new UsernamePasswordAuthenticationFilter(authenticationManager))
                .build();
    }

    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            response.sendRedirect("/");
        });
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

}
