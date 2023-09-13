package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.auth.AuthResponse;
import com.example.demo.auth.AuthService;
import com.example.demo.auth.LoginRequest;
import com.example.demo.auth.RegisterRequest;
import com.example.demo.entities.user.User;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor

public class SessionController {

    private final AuthService authService;
    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping(value = "login")
    public String renderLogin() {
        return "loginForm";
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestParam String username, String password) {

        System.out.println("////////// login request  //////////");
        System.out.println(username + password);
        System.out.println("////////// login request  //////////");

        LoginRequest logginAttemp = new LoginRequest(username, password);

        return ResponseEntity.ok(authService.login(logginAttemp));
    }

    @GetMapping(value = "register")
    public String renderRegister() {
        return "registerForm";
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestParam String username, String lastname, String firstname,
            String country,
            String password) {

        System.out.println("////////// register request //////////");
        System.out.println(username + lastname + firstname + country + password);
        System.out.println("////////// register request //////////");

        RegisterRequest registerAttemp = new RegisterRequest(username, password, firstname, lastname, country);
        AuthResponse auth = authService.register(registerAttemp);

        return ResponseEntity.ok(auth);
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSession() {
        String sessionId = "";
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

            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false); // Devuelve
                                                                                                           // la info de
                                                                                                           // todas las
                                                                                                           // sessiones,
                                                                                                           // no incluye
                                                                                                           // las
                                                                                                           // sessiones
                                                                                                           // expiradas

            System.out.println("********** sessionRegistry.getAllSessions(session, false) **********");
            System.out.println(sessionInformations);
            System.out.println("********** sessionRegistry.getAllSessions(session, false) **********");
            for (SessionInformation sessionInformation : sessionInformations) {
                sessionId = sessionInformation.getSessionId();
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("USUARIO LOGUEADO => sessionUser", userObject);
        return ResponseEntity.ok(response);

    }
}
