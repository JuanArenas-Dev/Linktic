package com.example.productos.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // Define el esquema de seguridad
        SecurityScheme apiKeyScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("X-API-KEY");

        // Agrega el esquema de seguridad a los componentes de OpenAPI
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("apiKeyScheme", apiKeyScheme))
                .addSecurityItem(new SecurityRequirement().addList("apiKeyScheme"))
                .info(new Info().title("API Productos").version("1.0").description("Documentación de la API de Productos"));
    }
}