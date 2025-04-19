package com.example.inventario.controller;

import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioRepository inventarioRepository;

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
        Inventario inventario = inventarioRepository.findByProductoId(productoId);
        if (inventario == null) return ResponseEntity.notFound().build();
        if (inventario.getCantidad() < cantidad)
            return ResponseEntity.badRequest().body("No hay suficiente stock");

        inventario.setCantidad(inventario.getCantidad() - cantidad);
        inventarioRepository.save(inventario);

        System.out.println("Inventario actualizado: " + inventario.getCantidad());
        return ResponseEntity.ok(inventario);
    }

    @PostMapping
    public Inventario crearInventario(@RequestBody Inventario inventario) {
        return inventarioRepository.save(inventario);
    }
}
