package com.example.productos.service;

import com.example.productos.model.Producto;
import com.example.productos.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    public ProductoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearProducto() {
        Producto producto = new Producto(null, "Producto 1", 100.0);
        Producto productoGuardado = new Producto(1L, "Producto 1", 100.0);

        when(productoRepository.save(producto)).thenReturn(productoGuardado);

        Producto resultado = productoService.crear(producto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Producto 1", resultado.getNombre());
        assertEquals(100.0, resultado.getPrecio());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testActualizarProducto() {
        Producto productoExistente = new Producto(1L, "Producto 1", 100.0);
        Producto productoActualizado = new Producto(1L, "Producto Actualizado", 150.0);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(productoExistente));
        when(productoRepository.save(productoExistente)).thenReturn(productoActualizado);

        Optional<Producto> resultado = productoService.actualizar(1L, productoActualizado);

        assertTrue(resultado.isPresent());
        assertEquals("Producto Actualizado", resultado.get().getNombre());
        assertEquals(150.0, resultado.get().getPrecio());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(productoExistente);
    }
}