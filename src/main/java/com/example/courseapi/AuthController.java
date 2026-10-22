package com.example.courseapi;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final String encodedPassword;

    public AuthController(
            JwtService jwtService,
            PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.encodedPassword =
                passwordEncoder.encode("student123");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        if ("student".equals(username)
                && password != null
                && passwordEncoder.matches(
                        password, encodedPassword)) {

            String token = jwtService.generateToken(username);

            return ResponseEntity.ok(
                    Map.of("token", token));
        }

        return ResponseEntity.status(401)
                .body(Map.of(
                        "message",
                        "Invalid username or password"));
    }
}