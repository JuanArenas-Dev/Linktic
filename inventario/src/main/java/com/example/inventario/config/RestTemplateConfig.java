package com.example.inventario.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.InterceptingHttpAccessor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfig {

    @Value("${productos.api.key}")
    private String productosApiKey;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Agregar interceptor para incluir el encabezado X-API-KEY
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((request, body, execution) -> {
            request.getHeaders().add("X-API-KEY", productosApiKey);
            return execution.execute(request, body);
        });

        ((InterceptingHttpAccessor) restTemplate).setInterceptors(interceptors);
        return restTemplate;
    }
}