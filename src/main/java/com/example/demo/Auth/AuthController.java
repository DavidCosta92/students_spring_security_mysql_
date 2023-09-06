package com.example.demo.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller // @RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login")
    public String renderLogin() {
        return "loginForm.html";
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "loginForm")
    public ResponseEntity<AuthResponse> loginForm(@RequestParam String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/register")
    public String renderRegister() {
        return "registerForm.html";
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse auth = authService.register(request);
        return ResponseEntity.ok(auth);
    }

    @PostMapping(value = "registerForm")
    public ResponseEntity<AuthResponse> registerForm(@RequestParam String username, String lastname, String firstname,
            String country,
            String password) {

        RegisterRequest request = new RegisterRequest(username, lastname, firstname, country, password);

        System.out.println("************** auth.token **************************");
        AuthResponse auth = authService.register(request);
        System.out.println(auth);
        System.out.println("************** auth.token **************************");

        return ResponseEntity.ok(auth);
    }

}
