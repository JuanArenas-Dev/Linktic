package com.example.inventario.controller;

import com.example.inventario.service.ExternalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8081")
@Tag(name = "Test", description = "Operaciones de prueba para manejo de timeout y reintentos basicos") 
@RestController
public class TestController {

    private final ExternalService externalService;

    public TestController(ExternalService externalService) {
        this.externalService = externalService;
    }

    @Operation(summary = "Probar reintentos", description = "Prueba el mecanismo de reintentos configurado en el servicio externo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reintento exitoso"),
            @ApiResponse(responseCode = "500", description = "Todos los intentos fallaron")
    })
    @GetMapping("/test-retry")
    public String testRetry() {
        return externalService.callExternalService();
    }
}