package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        // Define Bearer Security Scheme
        SecurityScheme bearerScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://9018.32procr.amypo.ai/")
                ))
                // Register security scheme
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", bearerScheme)
                )
                // Apply security globally
                .addSecurityItem(new SecurityRequirement()
                        .addList("BearerAuth")
                );
    }
}