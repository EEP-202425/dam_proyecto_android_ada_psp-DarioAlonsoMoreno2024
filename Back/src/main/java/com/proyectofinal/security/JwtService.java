package com.proyectofinal.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration-ms:3600000}")
    private long expirationMs;

    public String generateToken(String email, Long userId) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(exp)
                .claims(Map.of("userId", userId))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String expectedEmail) {
        String email = extractEmail(token);
        return email != null && email.equals(expectedEmail) && !isExpired(token);
    }

    public Long extractUserId(String token) {
        Object v = extractAllClaims(token).get("userId");
        if (v == null) return null;
        if (v instanceof Integer i) return i.longValue();
        if (v instanceof Long l) return l;
        return Long.valueOf(v.toString());
    }

    private boolean isExpired(String token) {
        Date exp = extractAllClaims(token).getExpiration();
        return exp != null && exp.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}