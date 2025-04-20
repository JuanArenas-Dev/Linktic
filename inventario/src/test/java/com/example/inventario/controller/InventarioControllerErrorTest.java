package com.example.inventario.controller;

import com.example.inventario.dto.ProductoDTO;
import com.example.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioControllerErrorTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private InventarioController inventarioController;

    public InventarioControllerErrorTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProductoNoEncontrado() {
        Long productoId = 1L;

        when(restTemplate.getForObject(anyString(), eq(ProductoDTO.class))).thenThrow(new RuntimeException("Producto no encontrado"));

        ResponseEntity<?> response = inventarioController.comprarProducto(productoId, 1);

        assertEquals(502, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("No se pudo validar el producto"));
        verify(restTemplate, times(1)).getForObject(anyString(), eq(ProductoDTO.class));
    }
}