package com.proyectofinal.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectofinal.dao.PedidoRepository;
import com.proyectofinal.dao.UsuarioRepository;
import com.proyectofinal.dominio.Pedido;
import com.proyectofinal.dominio.Usuario;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private InventarioService inventarioService;

	public List<Pedido> obtenerTodos() {
		return pedidoRepository.findAll();
	}

	public Pedido crearPedido(Long usuarioId) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		Pedido pedido = new Pedido();
		pedido.setUsuario(usuario);
		pedido.setFecha(LocalDate.now());

		return pedidoRepository.save(pedido);
	}

	public Pedido crearPedido(Long usuarioId, Long productoId, int cantidad) {
		Usuario usuario = usuarioRepository.findById(usuarioId)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		inventarioService.reducirStock(productoId, cantidad); // ¡resta stock!

		Pedido pedido = new Pedido();
		pedido.setUsuario(usuario);
		pedido.setFecha(LocalDate.now());
		// si agregamos producto/cantidad al pedido, también lo seteamos

		return pedidoRepository.save(pedido);
	}
}
