package com.Inventario.Inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inventario API - Clinica BUPA")
                        .version("7.0")
                        .description("API para gestión de inventario de prendas en la clínica BUPA, encontra información sobre prendas,stock, y más."));
    }
    
}
