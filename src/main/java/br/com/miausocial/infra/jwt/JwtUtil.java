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
        return generateToken(user, appProperties.getSecurity().getJwtExpirationMs());
    }

    public String generateRefreshToken(AppUser user) {
        return generateToken(user, appProperties.getSecurity().getJwtRefreshExpirationMs());
    }

    private String generateToken(AppUser user, long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.id().toString());
        claims.put("email", user.getEmail().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @FunctionalInterface
    private interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
