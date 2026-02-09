package com.proyectofinal.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyectofinal.dto.CarritoAddRequest;
import com.proyectofinal.dto.CarritoResponse;
import com.proyectofinal.services.CarritoService;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

	private final CarritoService carritoService;

	public CarritoController(CarritoService carritoService) {
		this.carritoService = carritoService;
	}

	@GetMapping
	public CarritoResponse ver() {
		return carritoService.verCarrito();
	}

	@PostMapping("/items")
	public CarritoResponse add(@RequestBody CarritoAddRequest request) {
		int cantidad = (request.getCantidad() != null) ? request.getCantidad() : 1;
		return carritoService.addProducto(request.getProductoId(), cantidad);
	}

	@PutMapping("/items/{itemId}")
	public CarritoResponse updateCantidad(@PathVariable Long itemId, @RequestBody CarritoAddRequest request) {
		int cantidad = (request.getCantidad() != null) ? request.getCantidad() : 1;
		return carritoService.updateCantidad(itemId, cantidad);
	}

	@DeleteMapping("/items/{itemId}")
	public CarritoResponse deleteItem(@PathVariable Long itemId) {
		return carritoService.deleteItem(itemId);
	}

	@DeleteMapping
	public ResponseEntity<Void> vaciar() {
		carritoService.vaciarCarrito();
		return ResponseEntity.noContent().build();
	}
}
