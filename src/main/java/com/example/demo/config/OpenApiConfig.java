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

        // Bearer token security scheme (this enables Authorize button)
        SecurityScheme bearerAuthScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        return new OpenAPI()
                // KEEP YOUR SERVER URL
                .servers(List.of(
                        new Server().url("https://9057.408procr.amypo.ai/")
                ))
                .info(new Info()
                        .title("Your API Docs")
                        .version("1.0.0")
                )
                .components(new Components()
                        .addSecuritySchemes("bearerAuth", bearerAuthScheme)
                )
                //  THIS IS WHAT MAKES AUTHORIZE BUTTON APPEAR
                .addSecurityItem(new SecurityRequirement()
                        .addList("bearerAuth")
                );
    }
}