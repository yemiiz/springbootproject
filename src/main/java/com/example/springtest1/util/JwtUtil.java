package com.example.springtest1.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "my-secret-key-springboot-project-2026-secure";

    private static final long EXPIRATION = 7 * 24 * 60 * 60 * 1000;

    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public static String generateToken(Long userId){
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(KEY)
                .compact();
    }

    public static Long validateToken(String token){
        try {
            String userId = Jwts.parser()
                    .verifyWith(KEY).build()
                    .parseSignedClaims(token)
                    .getPayload().getSubject();
            return Long.parseLong(userId);
        } catch (Exception e) {
            return null;
        }
    }
}
