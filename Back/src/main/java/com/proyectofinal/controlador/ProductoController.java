package com.proyectofinal.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyectofinal.dominio.Producto;
import com.proyectofinal.services.ProductoService;

@RestController
@RequestMapping("api/producto")
public class ProductoController {
	
	@Autowired
	private ProductoService service;
	
	 @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    public Producto crear(@RequestBody Producto producto) {
	        return service.crearProducto(producto);
	 }
	 
	  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	    public Producto obtenerPorId(@PathVariable(value = "id") Long id) {
	        return service.obtenerProducto(id);
	    }

	    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Producto> obtenerTodos() {
	        return service.obtenerTodosLosProductos();
	    }

	    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    public Producto actualizar(@PathVariable(value = "id") Long id, @RequestBody Producto producto) {
	        return service.actualizarProducto(producto, id);
	    }

	    @DeleteMapping(path = "/{id}")
	    public void eliminar(@PathVariable(value = "id") Long id) {
	        service.eliminarProducto(id);
	    }
}
