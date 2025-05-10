package com.proyectofinal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectofinal.dao.InventarioRepository;
import com.proyectofinal.dao.ProductRepository;
import com.proyectofinal.dominio.Inventario;
import com.proyectofinal.dominio.Producto;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Inventario> obtenerInventario() {
        return inventarioRepository.findAll();
    }

    public Inventario agregarStock(Long productoId, int cantidad) {
        Producto producto = productRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inventario inventario = inventarioRepository.findByProducto(producto)
            .orElse(new Inventario());

        inventario.setProducto(producto);
        inventario.setStockActual(inventario.getStockActual() + cantidad);

        return inventarioRepository.save(inventario);
    }
    
    public boolean reducirStock(Long productoId, int cantidad) {
        Producto producto = productRepository.findById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Inventario inventario = inventarioRepository.findByProducto(producto)
            .orElseThrow(() -> new RuntimeException("No hay inventario para este producto"));

        int stockActual = inventario.getStockActual();
        if (stockActual < cantidad) {
            throw new RuntimeException("Stock insuficiente" + producto.getNombre());
        }

        inventario.setStockActual(stockActual - cantidad);

        inventarioRepository.save(inventario);
        return true;
    }
}
