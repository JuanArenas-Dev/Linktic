package com.example.productos.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class ExternalService {

    @Retryable(
        value = { RuntimeException.class }, // Excepciones que activan el reintento
        maxAttempts = 3, // Número máximo de intentos
        backoff = @Backoff(delay = 2000) // Tiempo de espera entre intentos (en milisegundos)
    )
    public String callExternalService() {
        // Simula una llamada a un servicio externo
        System.out.println("Intentando llamar al servicio externo...");
        throw new RuntimeException("Fallo en el servicio externo"); // Simula un fallo
    }

    @Recover
    public String recover(RuntimeException e) {
        System.out.println("Todos los intentos fallaron. Recuperando...");
        return "Respuesta por defecto";
    }
}