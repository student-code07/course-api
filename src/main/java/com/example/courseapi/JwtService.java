package com.example.courseapi;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final SecretKey secretKey = Keys.hmacShaKeyFor(
        "Week5SpringSecurityJWTSecretKeyMustBeAtLeastThirtyTwoBytesLong!"
            .getBytes(java.nio.charset.StandardCharsets.UTF_8)
    );

    public String generateToken(String username) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
            .subject(username)
            .issuedAt(new Date(now))
            .expiration(new Date(now + 60 * 60 * 1000))
            .signWith(secretKey)
            .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }
}
