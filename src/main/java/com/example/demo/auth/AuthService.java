package com.example.demo.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entities.user.Role;
import com.example.demo.entities.user.User;
import com.example.demo.entities.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    // private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private SessionRegistry sessionRegistry;

    public AuthResponse login(LoginRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        // String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .responseText("PRUEBA DE RESPONSE TEXT USUARIO LOGUEADO.toString()=>" + user.toString())
                .build();

    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.lastname)
                .country(request.getCountry())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .responseText("PRUEBA DE RESPONSE TEXT USUARIO CREADO.toString()=>" + user.toString())
                .build();

    }

    public User getCurrentSession() {
        User userObject = null; // user propio
        List<Object> sessions = sessionRegistry.getAllPrincipals(); // listado de objetos representando todas las
                                                                    // sessiones de la app
        for (Object session : sessions) {
            if (session instanceof User) {
                userObject = (User) session; // recuperamos el usuario authenticado
                System.out.println("------------------ recuperamos el usuario authenticado ------------------");
                System.out.println(session);
                System.out.println("------------------ recuperamos el usuario authenticado ------------------");
            }

        }
        return userObject;
    }

}
