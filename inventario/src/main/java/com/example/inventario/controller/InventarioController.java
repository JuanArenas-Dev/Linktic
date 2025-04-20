package com.example.inventario.controller;

import com.example.inventario.dto.ProductoDTO;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8082")
@Tag(name = "Inventarios", description = "Operaciones relacionadas con inventarios")
@RestController
@RequestMapping("/api/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioRepository inventarioRepository;
    private final RestTemplate restTemplate;
    private final String PRODUCTO_API_URL = "http://localhost:8081/api/productos";

    @Operation(summary = "Listar inventarios", description = "Devuelve una lista de todos los inventarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de inventarios obtenida exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class)))
    })
    @GetMapping
    public List<Inventario> getAll() {
        return inventarioRepository.findAll();
    }

    @Operation(summary = "Obtener inventario por ID de producto", description = "Devuelve el inventario de un producto específico obtenido por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado", content = @Content)
    })
    @GetMapping("/{productoId}")
    public ResponseEntity<Inventario> getByProductoId(@PathVariable Long productoId) {
        Inventario inventario = inventarioRepository.findByProductoId(productoId);
        if (inventario == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inventario);
    }

    @Operation(summary = "Comprar producto", description = "Reduce la cantidad de inventario de un producto al realizar la simulacion de una compra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra realizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "400", description = "No hay suficiente stock", content = @Content),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado", content = @Content),
            @ApiResponse(responseCode = "502", description = "Error al validar el producto", content = @Content)
    })
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

    @Operation(summary = "Crear inventario", description = "Crea un nuevo registro de inventario para un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))),
            @ApiResponse(responseCode = "400", description = "El producto no existe", content = @Content),
            @ApiResponse(responseCode = "502", description = "Error al validar el producto", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> crearInventario(@RequestBody Inventario inventario) {
        try {
            ProductoDTO producto = restTemplate.getForObject(PRODUCTO_API_URL + "/" + inventario.getProductoId(), ProductoDTO.class);
            if (producto == null) return ResponseEntity.badRequest().body("El producto no existe");
        } catch (Exception e) {
            return ResponseEntity.status(502).body("No se puede validar el producto: " + e.getMessage());
        }

        return ResponseEntity.ok(inventarioRepository.save(inventario));
    }
}
