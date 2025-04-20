package com.example.productos.controller;

import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = service.crear(producto);
        return ResponseEntity.ok(toJsonApi(nuevo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(p -> ResponseEntity.ok(toJsonApi(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<?> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        List<Producto> productos = service.obtenerTodos(page, size);
        Map<String, Object> response = new HashMap<>();
        List<Object> data = new ArrayList<>();

        for (Producto p : productos) {
            data.add(toJsonApi(p).get("data"));
        }

        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return service.actualizar(id, producto)
                .map(p -> ResponseEntity.ok(toJsonApi(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private Map<String, Object> toJsonApi(Producto producto) {
        Map<String, Object> json = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("type", "productos");
        data.put("id", producto.getId());
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("nombre", producto.getNombre());
        attributes.put("precio", producto.getPrecio());
        data.put("attributes", attributes);
        json.put("data", data);
        return json;
    }
}
