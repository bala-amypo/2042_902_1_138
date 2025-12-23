package com.example.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        // Security Scheme for JWT Bearer Token
        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        return new OpenAPI()
                .servers(List.of(
                        new Server().url("https://9057.408procr.amypo.ai/") // local dev server
                        // you can add more servers if needed
                        // new Server().url("https://your-production-url.com")
                ))
                .info(new Info()
                        .title("Demo Project API Documentation")
                        .version("1.0.0")
                        .description("API docs for AuthController and GuestController endpoints")
                )
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", bearerAuthScheme)
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")
                );
    }
}
