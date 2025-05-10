package com.proyectofinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyectofinal.dominio.Pedido;
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

}
