package com.proyectofinal.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectofinal.dao.InventarioRepository;
import com.proyectofinal.dao.ProductRepository;
import com.proyectofinal.dominio.Inventario;
import com.proyectofinal.dominio.Producto;
import com.proyectofinal.dto.ProductoConStockDTO;

@Service
public class ProductoService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private InventarioRepository inventarioRepository;
	
	@Transactional
	public Producto crearProducto(Producto producto) {
		return repository.save(producto);
	}
	
	public List<ProductoConStockDTO> obtenerProductosConStock() {
	    List<Producto> productos = repository.findAll();
	    List<ProductoConStockDTO> resultado = new ArrayList<>();

	    for (Producto producto : productos) {
	        Inventario inventario = inventarioRepository.findByProducto(producto)
	                .orElse(new Inventario()); // por si no hay stock aún
	        int stock = inventario.getStockActual();
	        resultado.add(new ProductoConStockDTO(producto.getNombre(), producto.getPrecio(), stock));
	    }

	    return resultado;
	}
	
	public Producto obtenerProducto(Long id) {
		return repository.findById(id).orElse(null);
	}
	
	public List<Producto> obtenerTodosLosProductos(){
		return repository.findAll();
	}
	
	@Transactional
	public Producto actualizarProducto(Producto producto, Long id) {
		Producto productoExistente = obtenerProducto(id);
		if(productoExistente != null) {
			producto.setId(id);
			return repository.save(producto);
		}
		  throw new RuntimeException("Producto no encontrado");
	}
	
	 @Transactional
	    public void eliminarProducto(Long id) {
	        repository.deleteById(id);
	    }

}
