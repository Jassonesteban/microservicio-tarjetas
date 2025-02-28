package com.microservicio_tarjetas.api.infrastructure.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class JwTokenFilter implements WebFilter {

    private static final String SECRET_KEY = "YXJmYjR2NXc2eTZ6N3g5MDBhc2RmZ2hqa2xtbnBxcnN0dXZ3eHl6MTIzNDU2Nzg5MA==";

    private static SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        if (path.equals("/api/v1/tarjetas/create")) {
            String token = getTokenFromRequest(exchange);

            if (token == null || !validateToken(token)) {
                System.out.println("⛔ Token inválido o ausente en: " + path);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            System.out.println("✅ Token válido para la ruta: " + path);

            Claims claims = parseClaims(token);
            String username = claims.getSubject();

            Authentication auth = new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());

            SecurityContext context = new SecurityContextImpl(auth);

            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
        }

        return chain.filter(exchange);
    }


    private String getTokenFromRequest(ServerWebExchange exchange) {
        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("🔍 Authorization Header: " + header);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            boolean expired = isTokenExpired(token);
            System.out.println("Token Expired: " + expired);
            Claims claims = parseClaims(token);
            System.out.println("Token Claims: " + claims);
            return !expired && claims != null;
        } catch (JwtException e) {
            System.out.println("Token Validation Error: " + e.getMessage());
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
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            System.out.println("Error al analizar el token: " + e.getMessage());
            throw e;
        }
    }
}
