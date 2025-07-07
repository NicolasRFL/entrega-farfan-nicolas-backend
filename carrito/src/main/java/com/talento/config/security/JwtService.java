package com.talento.config.security;
import com.talento.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {
    private final String SECRET_KEY = "clave-de-al-menos-treinta-y-dos-caracteres-para-seguridad";
    private final long EXPIRATION_TIME = 1000 * 60 * 60;

    // 1. Generar token desde usuario
    public String generateToken(Usuario usuario) {
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(usuario.getEmail())
                .claim("rol", usuario.getRol().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // 2. Obtener email desde token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 3. Obtener rol desde token
    public String extractRol(String token) {
        return extractAllClaims(token).get("rol", String.class);
    }

    // 4. Validar token
    public boolean isTokenValid(String token, Usuario usuario) {
        final String email = extractEmail(token);
        return (email.equals(usuario.getEmail())) && !isTokenExpired(token);
    }

    // Helpers internos

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(getSigningKey())
                .build();
        return parser.parseSignedClaims(token).getPayload();
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
