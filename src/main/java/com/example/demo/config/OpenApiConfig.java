package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Hotel Key Management API")
                        .version("1.0")
                        .description("API for Hotel Room Key Management System"))
                .servers(List.of(
                        new Server().url("https://9030.pro604cr.amypo.ai/").description("Production Server"),
                        new Server().url("http://localhost:9001").description("Local Development")
                ));
    }
}