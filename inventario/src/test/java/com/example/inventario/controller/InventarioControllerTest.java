package com.example.inventario.controller;

import com.example.inventario.dto.ProductoDTO;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioControllerTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private InventarioController inventarioController;

    public InventarioControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testComprarProducto() {
        Long productoId = 1L;
        int cantidad = 2;

        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(productoId);
        productoDTO.setNombre("Producto 1");
        productoDTO.setPrecio(100.0);

        Inventario inventario = new Inventario(1L, productoId, 10);

        when(restTemplate.getForObject(anyString(), eq(ProductoDTO.class))).thenReturn(productoDTO);
        when(inventarioRepository.findByProductoId(productoId)).thenReturn(inventario);
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        ResponseEntity<?> response = inventarioController.comprarProducto(productoId, cantidad);

        assertEquals(200, response.getStatusCodeValue());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(ProductoDTO.class));
        verify(inventarioRepository, times(1)).findByProductoId(productoId);
        verify(inventarioRepository, times(1)).save(any(Inventario.class));
    }
}