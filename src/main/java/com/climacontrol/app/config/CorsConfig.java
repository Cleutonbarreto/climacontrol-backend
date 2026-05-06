package com.climacontrol.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // FRONTEND PERMITIDO (APENAS ESTE)
        config.setAllowedOrigins(List.of("http://localhost:4200"));

        // MÉTODOS EXATOS QUE SUA API USA
        config.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "OPTIONS"
        ));

        // HEADERS NECESSÁRIOS (incluindo JWT)
        config.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type"
        ));

        // NÃO PRECISA para JWT via header
        config.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}

