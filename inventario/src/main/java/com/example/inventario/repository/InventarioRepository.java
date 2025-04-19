package com.example.inventario.repository;

import com.example.inventario.model.Inventario; // Importar el modelo correspondiente
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Inventario findByProductoId(Long productoId); // Método para encontrar un inventario por su ID
    
}
