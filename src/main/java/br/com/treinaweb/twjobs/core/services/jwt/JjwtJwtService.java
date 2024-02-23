package br.com.treinaweb.twjobs.core.services.jwt;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import br.com.treinaweb.twjobs.config.JwtConfigProperties;
import br.com.treinaweb.twjobs.core.exceptions.JwtServiceException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JjwtJwtService implements JwtService {

    private final JwtConfigProperties configProperties;

    @Override
    public String generateAccessToken(String sub) {
        var now = Instant.now();
        var expiration = now.plusSeconds(configProperties.getAccessExpiresIn());
        var key = Keys.hmacShaKeyFor(configProperties.getAccessSecret().getBytes());
        return Jwts.builder()
            .subject(sub)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(key)
            .compact();
    }

    @Override
    public String getSubFromAccessToken(String token) {
        var key = Keys.hmacShaKeyFor(configProperties.getAccessSecret().getBytes());
        try {
            return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        } catch (JwtException e) {
            throw new JwtServiceException(e.getLocalizedMessage());
        }
    }
    
}
