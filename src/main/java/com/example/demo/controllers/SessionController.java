package com.example.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class SessionController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/login")
    public String renderLogin() {
        return "loginForm";
    }

    @GetMapping("/register")
    public String renderRegister() {
        return "registerForm";
    }

    @GetMapping("/session")
    public ResponseEntity<?> getDetailsSession() {
        String sessionId = "";
        User userObject = null; // user definido por defecto en spring

        List<Object> sessions = sessionRegistry.getAllPrincipals(); // listado de objetos representando todas las
                                                                    // sessiones de la app

        for (Object session : sessions) {
            if (session instanceof User) {
                userObject = (User) session; // recuperamos el usuario authenticado
            }

            List<SessionInformation> sessionInformations = sessionRegistry.getAllSessions(session, false); // Devuelve
                                                                                                           // la info de
                                                                                                           // todas las
                                                                                                           // sessiones,
                                                                                                           // no incluye
                                                                                                           // las
                                                                                                           // sessiones
                                                                                                           // expiradas
            for (SessionInformation sessionInformation : sessionInformations) {
                sessionId = sessionInformation.getSessionId();
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("response", "hello wolrd");
        response.put("sessionId", sessionId);
        response.put("sessionUser", userObject);
        return ResponseEntity.ok(response);

    }
}
