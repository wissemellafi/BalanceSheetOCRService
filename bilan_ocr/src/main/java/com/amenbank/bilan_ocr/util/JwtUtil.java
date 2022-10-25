package com.amenbank.bilan_ocr.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    private final SecretKey key;
    private Claims claims;
    public JwtUtil(String signingKey) {
        key = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject, Map<String, Object> claims, String issuer, Date expiration) {
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setExpiration(expiration)
                .setIssuer(issuer)
                .signWith(key)
                .compact();
    }

    public boolean verifyToken(String token) {
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            claims = null;
        }

        return claims != null;
    }

    public String getUsernameFromToken() {
        if (claims != null) {
            return claims.getSubject();
        }

        return null;
    }

    public String getRoleFromToken() {
        if (claims != null) {
            return  (String) this.claims.get("role");
        }

        return null;
    }
}
