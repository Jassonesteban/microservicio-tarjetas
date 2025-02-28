package com.microservicio_tarjetas.api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JwTokenFilter jwTokenFilter;

    public SecurityConfig(JwTokenFilter jwTokenFilter) {
        this.jwTokenFilter = jwTokenFilter;
    }

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers("/api/v1/tarjetas/create").authenticated()
                        .anyExchange().permitAll()
                )
                .addFilterAt(jwTokenFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
