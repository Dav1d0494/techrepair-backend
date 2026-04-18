package com.techrepair.backend.security;

import com.techrepair.backend.config.JwtConfig;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Autowired
    private JwtConfig jwtConfig;

    public String generateToken(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtConfig.getExpiration());
        byte[] keyBytes = jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(Keys.hmacShaKeyFor(keyBytes))
                .compact();
    }

    public String getUserEmailFromToken(String token) {
        byte[] keyBytes = jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(keyBytes))
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            byte[] keyBytes = jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8);
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}
