package com.climacontrol.app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET_KEY =
            "pesopro-chave-jwt-segura-com-256-bits-no-minimo";

    private static final long EXPIRACAO = 1000 * 60 * 60; // 1 hora

    private Key getChaveAssinatura() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ===============================
    // Geração do token
    // ===============================
    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACAO))
                .signWith(getChaveAssinatura(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ===============================
    // Validação
    // ===============================
    public boolean tokenValido(String token) {
        try {
            extrairClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ===============================
    // Extrações
    // ===============================
    public String extrairEmail(String token) {
        return extrairClaim(token, Claims::getSubject);
    }

    private <T> T extrairClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extrairClaims(token);
        return resolver.apply(claims);
    }

    private Claims extrairClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getChaveAssinatura())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean tokenJwtValido(String token) {
        try {
            extrairClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extrairEmailSeguro(String token) {
        return extrairEmail(token);
    }
}