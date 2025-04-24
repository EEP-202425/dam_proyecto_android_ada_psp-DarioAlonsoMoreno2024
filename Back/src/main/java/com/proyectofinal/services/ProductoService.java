package com.proyectofinal.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectofinal.dao.ProductRepository;
import com.proyectofinal.dominio.Producto;

@Service
public class ProductoService {
	
	@Autowired
	private ProductRepository repository;
	
	@Transactional
	public Producto crearProducto(Producto producto) {
		return repository.save(producto);
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
