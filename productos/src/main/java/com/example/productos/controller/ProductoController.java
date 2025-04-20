package com.example.productos.controller;

import com.example.productos.model.Producto;
import com.example.productos.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto en la base de datos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content)
    })
    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody Producto producto) {
        Producto nuevo = service.crear(producto);
        return ResponseEntity.ok(toJsonApi(nuevo));
    }

    @Operation(summary = "Obtener un producto por ID", description = "Devuelve un producto específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProducto(@PathVariable Long id) {
        return service.obtenerPorId(id)
                .map(p -> ResponseEntity.ok(toJsonApi(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Listar productos", description = "Devuelve una lista paginada de productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de productos",
                    content = @Content(mediaType = "application/json"))
    })
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

    @Operation(summary = "Actualizar un producto", description = "Actualiza los datos de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return service.actualizar(id, producto)
                .map(p -> ResponseEntity.ok(toJsonApi(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado", content = @Content)
    })
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
