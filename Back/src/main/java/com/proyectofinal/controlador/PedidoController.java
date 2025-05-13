package com.proyectofinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyectofinal.dominio.Pedido;
import com.proyectofinal.dto.PedidoRequest;
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

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedido(
            @RequestParam Long usuarioId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {
        try {
            Pedido pedido = pedidoService.crearPedido(usuarioId, productoId, cantidad);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(
            @RequestBody PedidoRequest request
    ) {
        // Llamas a tu servicio con los tres parámetros:
        Pedido nuevo = pedidoService.crearPedido(
            request.getUsuarioId(),
            request.getProductoId(),
            request.getCantidad()
        );

        // Mapea tu entidad a DTO de salida
        PedidoResponse resp = new PedidoResponse(
            nuevo.getId(),
            nuevo.getFecha(),
            nuevo.getUsuario().getId(),    // o nuevo.getUsuarioId() si usas IDs
            nuevo.getProducto().getId()   // o nuevo.getProductoId()
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(resp);
    }
}