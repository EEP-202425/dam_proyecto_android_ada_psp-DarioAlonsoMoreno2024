package com.proyectofinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyectofinal.dominio.Pedido;
import com.proyectofinal.dto.ConfirmPedidoResponse;
import com.proyectofinal.dto.PedidoRequest;
import com.proyectofinal.dto.PedidoResumenDTO;
import com.proyectofinal.dto.PedidoResponse;
import com.proyectofinal.services.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;

	@GetMapping
	public List<Pedido> obtenerTodos() {
		return pedidoService.obtenerTodos();
	}

	// Mantengo el POST individual por compatibilidad
	@PostMapping
	public ResponseEntity<PedidoResponse> crearPedido(@RequestBody PedidoRequest request) {

		int cantidad = (request.getCantidad() != null) ? request.getCantidad() : 1;

		Pedido nuevo = pedidoService.crearPedido(request.getUsuarioId(), request.getProductoId(), cantidad);

		PedidoResponse resp = new PedidoResponse(nuevo.getId(), nuevo.getFecha(), nuevo.getUsuario().getId(),
				nuevo.getProducto().getId(), nuevo.getCantidad());

		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}

	@PostMapping("/confirmar")
	public ResponseEntity<ConfirmPedidoResponse> confirmarDesdeCarrito() {
		ConfirmPedidoResponse resp = pedidoService.confirmarPedidoDesdeCarrito();
		return ResponseEntity.status(HttpStatus.CREATED).body(resp);
	}

	// AHORA: mis pedidos "listos para Android"
	@GetMapping("/mis")
	public List<PedidoResumenDTO> misPedidos() {
		return pedidoService.misPedidosResumen();
	}

	@GetMapping("/mis/{pedidoId}")
	public PedidoResumenDTO detalleMiPedido(@PathVariable Long pedidoId) {
		return pedidoService.detalleMiPedidoResumen(pedidoId);
	}
}
