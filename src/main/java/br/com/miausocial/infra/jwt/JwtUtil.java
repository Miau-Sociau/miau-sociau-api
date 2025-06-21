package br.com.miausocial.infra.jwt;

import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.infra.config.AppProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final AppProperties appProperties;
    private final SecretKey key;

    public String generateToken(AppUser user) {
        return generateToken(user, false);
    }

    public String generateRefreshToken(AppUser user) {
        return generateToken(user, true);
    }

    private String generateToken(AppUser user, boolean isRefreshToken) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", isRefreshToken ? "REFRESH" : "ACCESS");
        claims.put("userId", user.id().toString());

        long expirationTime = isRefreshToken ? 
            appProperties.getSecurity().getJwtExpirationMs() * 7 : // 7 days for refresh token
            appProperties.getSecurity().getJwtExpirationMs();     // 1 day for access token

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractUserId(String token) {
        return extractClaims(token).get("userId", String.class);
    }

    public boolean isRefreshToken(String token) {
        return "REFRESH".equals(extractClaims(token).get("type", String.class));
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado", e);
        } catch (JwtException e) {
            throw new RuntimeException("Token inválido", e);
        }
    }
}
