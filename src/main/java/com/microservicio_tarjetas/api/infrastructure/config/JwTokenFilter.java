package com.microservicio_tarjetas.api.infrastructure.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwTokenFilter implements WebFilter {

    private static final String SECRET_KEY = "YXJmYjR2NXc2eTZ6N3g5MDBhc2RmZ2hqa2xtbnBxcnN0dXZ3eHl6MTIzNDU2Nzg5MA==";

    private static SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = getTokenFromRequest(exchange);
        if (token != null && validateToken(token)) {
            return chain.filter(exchange);
        } else {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized: Invalid or missing token"));
        }
    }


    private String getTokenFromRequest(ServerWebExchange  exchange) {
        String header = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token) && parseClaims(token) != null;
        } catch (JwtException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration != null && expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    private static Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
