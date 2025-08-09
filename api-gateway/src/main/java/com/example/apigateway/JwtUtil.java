package com.example.apigateway;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "mysecretkey";

    public static String generateToken(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .compact();
    }

    public static void validateToken(String token) {
        try {
            Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Invalid token");
        }
    }
}
