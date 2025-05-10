package com.proyectofinal.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyectofinal.dominio.Inventario;
import com.proyectofinal.services.InventarioService;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> obtenerInventario() {
        return inventarioService.obtenerInventario();
    }

    @PostMapping("/agregar")
    public ResponseEntity<?> agregarStock(
            @RequestParam Long productoId,
            @RequestParam int cantidad) {
        Inventario inventario = inventarioService.agregarStock(productoId, cantidad);
        return ResponseEntity.ok(inventario);
    }
}
