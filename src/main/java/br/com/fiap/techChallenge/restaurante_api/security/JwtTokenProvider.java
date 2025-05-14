package br.com.fiap.techChallenge.restaurante_api.security;

import br.com.fiap.techChallenge.restaurante_api.domain.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key jwtSecretKey;
    private final long jwtExpirationMs;

    public JwtTokenProvider(
            @Value("${app.security.jwt.secret-key}") String jwtSecret,
            @Value("${app.security.jwt.expiration-ms}") long jwtExpirationMs) {
        try {
            this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new IllegalArgumentException("Falha ao criar a chave JWT. Verifique se 'app.security.jwt.secret-key' tem no mínimo 32 caracteres.", e);
        }
        this.jwtExpirationMs = jwtExpirationMs;
    }


    public String generateToken(Usuario user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("userId", user.getId())
                .claim("userType", user.getUserType().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)  // <-- Corrigido aqui
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
