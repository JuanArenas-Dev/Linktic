package com.example.inventario.controller;

import com.example.inventario.dto.ProductoDTO;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioRepository inventarioRepository;
    private final RestTemplate restTemplate;
    private final String PRODUCTO_API_URL = "http://localhost:8081/api/productos";

    @GetMapping
    public List<Inventario> getAll() {
        return inventarioRepository.findAll();
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<Inventario> getByProductoId(@PathVariable Long productoId) {
        Inventario inventario = inventarioRepository.findByProductoId(productoId);
        if (inventario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inventario);
    }

    @PutMapping("/{productoId}/comprar")
    public ResponseEntity<?> comprarProducto(@PathVariable Long productoId, @RequestParam int cantidad) {
        try {
            restTemplate.getForObject(PRODUCTO_API_URL + "/" + productoId, ProductoDTO.class);
        } catch (Exception e) {
            return ResponseEntity.status(502).body("No se pudo validar el producto: " + e.getMessage());
        }

        Inventario inventario = inventarioRepository.findByProductoId(productoId);
        if (inventario == null) return ResponseEntity.notFound().build();
        if (inventario.getCantidad() < cantidad)
            return ResponseEntity.badRequest().body("No hay suficiente stock");

        inventario.setCantidad(inventario.getCantidad() - cantidad);
        inventarioRepository.save(inventario);

        System.out.println("Inventario actualizado para producto " + productoId + ": " + inventario.getCantidad());
        return ResponseEntity.ok(inventario);
    }


    @PostMapping
    public ResponseEntity<?> crearInventario(@RequestBody Inventario inventario) {

        try{
            ProductoDTO producto = restTemplate.getForObject(PRODUCTO_API_URL + "/" + inventario.getProductoId(), ProductoDTO.class);
            if (producto == null) return ResponseEntity.badRequest().body("El producto no existe");

        }catch(Exception e){
            return ResponseEntity.status(502).body("No se puede validar el producto: "+e.getMessage());
        }

        return ResponseEntity.ok(inventarioRepository.save(inventario));
    }
}
