package com.example.productos.service;

import com.example.productos.model.*;
import com.example.productos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired  
    private ProductoRepository repository;

    public Producto crear(Producto producto){
        return repository.save(producto);
    }

    public Optional<Producto> obtenerPorId(Long id){
        return repository.findById(id);

    }

    public List<Producto> obtenerTodos(int page, int size){
        return repository.findAll(PageRequest.of(page, size)).getContent();
    }

    public Optional<Producto> actualizar(Long id, Producto productoActualizado){
        return repository.findById(id).map(producto->{
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            return repository.save(producto);

        });
    }

    public void eliminar(Long id){
        repository.deleteById(id);
    }

    
}
