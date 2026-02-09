package com.proyectofinal.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.proyectofinal.dao.PedidoRepository;
import com.proyectofinal.dao.ProductRepository;
import com.proyectofinal.dao.UsuarioRepository;
import com.proyectofinal.dominio.Pedido;
import com.proyectofinal.dominio.Producto;
import com.proyectofinal.dominio.Usuario;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductRepository productoRepo;
    private final UsuarioRepository usuarioRepo;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProductRepository productoRepo,
                         UsuarioRepository usuarioRepo) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepo = productoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido crearPedido(Long usuarioId, Long productoId, int cantidad) {

        if (usuarioId == null) {
            throw new RuntimeException("usuarioId es obligatorio");
        }
        if (productoId == null) {
            throw new RuntimeException("productoId es obligatorio");
        }
        if (cantidad <= 0) {
            throw new RuntimeException("cantidad debe ser mayor que 0");
        }

        Usuario u = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no existe"));

        Producto p = productoRepo.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no existe"));

        Pedido pedido = new Pedido();
        pedido.setUsuario(u);
        pedido.setProducto(p);
        pedido.setFecha(LocalDate.now());

        // Si tu entidad Pedido tiene el campo cantidad, descomenta esta línea:
        // pedido.setCantidad(cantidad);

        return pedidoRepository.save(pedido);
    }
}