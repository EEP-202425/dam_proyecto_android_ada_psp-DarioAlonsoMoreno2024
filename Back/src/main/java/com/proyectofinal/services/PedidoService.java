package com.proyectofinal.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proyectofinal.dao.CarritoItemRepository;
import com.proyectofinal.dao.PedidoRepository;
import com.proyectofinal.dao.ProductRepository;
import com.proyectofinal.dao.UsuarioRepository;
import com.proyectofinal.dominio.CarritoItem;
import com.proyectofinal.dominio.Pedido;
import com.proyectofinal.dominio.Producto;
import com.proyectofinal.dominio.Usuario;
import com.proyectofinal.dto.ConfirmPedidoResponse;
import com.proyectofinal.dto.PedidoResumenDTO;
import com.proyectofinal.dto.PedidoResponse;

@Service
public class PedidoService {

	private final PedidoRepository pedidoRepository;
	private final ProductRepository productoRepo;
	private final UsuarioRepository usuarioRepo;
	private final CarritoItemRepository carritoRepo;

	public PedidoService(PedidoRepository pedidoRepository, ProductRepository productoRepo,
			UsuarioRepository usuarioRepo, CarritoItemRepository carritoRepo) {
		this.pedidoRepository = pedidoRepository;
		this.productoRepo = productoRepo;
		this.usuarioRepo = usuarioRepo;
		this.carritoRepo = carritoRepo;
	}

	public List<Pedido> obtenerTodos() {
		return pedidoRepository.findAll();
	}

	public Pedido crearPedido(Long usuarioId, Long productoId, int cantidad) {

		if (usuarioId == null)
			throw new RuntimeException("usuarioId es obligatorio");
		if (productoId == null)
			throw new RuntimeException("productoId es obligatorio");
		if (cantidad <= 0)
			throw new RuntimeException("cantidad debe ser mayor que 0");

		Usuario u = usuarioRepo.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no existe"));

		Producto p = productoRepo.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no existe"));

		if (p.getStock() < cantidad) {
			throw new RuntimeException("Stock insuficiente para el producto " + p.getId());
		}

		// descontar stock
		p.setStock(p.getStock() - cantidad);
		productoRepo.save(p);

		Pedido pedido = new Pedido();
		pedido.setUsuario(u);
		pedido.setProducto(p);
		pedido.setFecha(LocalDate.now());
		pedido.setCantidad(cantidad);

		return pedidoRepository.save(pedido);
	}

	@Transactional
	public ConfirmPedidoResponse confirmarPedidoDesdeCarrito() {

		Usuario u = currentUser();

		List<CarritoItem> items = carritoRepo.findByUsuario_Id(u.getId());
		if (items.isEmpty()) {
			throw new RuntimeException("El carrito está vacío");
		}

		// Validación de stock previa
		for (CarritoItem ci : items) {
			Producto fresh = productoRepo.findById(ci.getProducto().getId())
					.orElseThrow(() -> new RuntimeException("Producto no existe: " + ci.getProducto().getId()));

			int cantidad = ci.getCantidad();
			if (fresh.getStock() < cantidad) {
				throw new RuntimeException("Stock insuficiente para el producto " + fresh.getId() + ". Stock="
						+ fresh.getStock() + ", solicitado=" + cantidad);
			}
		}

		double total = 0.0;
		List<PedidoResponse> pedidosCreados = new ArrayList<>();

		for (CarritoItem ci : items) {
			Producto fresh = productoRepo.findById(ci.getProducto().getId())
					.orElseThrow(() -> new RuntimeException("Producto no existe: " + ci.getProducto().getId()));

			int cantidad = ci.getCantidad();

			fresh.setStock(fresh.getStock() - cantidad);
			productoRepo.save(fresh);

			Pedido pedido = new Pedido();
			pedido.setUsuario(u);
			pedido.setProducto(fresh);
			pedido.setFecha(LocalDate.now());
			pedido.setCantidad(cantidad);

			Pedido saved = pedidoRepository.save(pedido);

			total += fresh.getPrecio() * cantidad;

			pedidosCreados.add(new PedidoResponse(saved.getId(), saved.getFecha(), saved.getUsuario().getId(),
					saved.getProducto().getId(), saved.getCantidad()));
		}

		carritoRepo.deleteByUsuario_Id(u.getId());

		String orderNumber = java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();
		return new ConfirmPedidoResponse(orderNumber, pedidosCreados, total, items.size());
	}

	// NUEVO: Mis pedidos con info del producto
	public List<PedidoResumenDTO> misPedidosResumen() {
		Usuario u = currentUser();

		return pedidoRepository.findByUsuario_IdOrderByIdDesc(u.getId()).stream().map(p -> {
			Producto prod = p.getProducto();
			double subtotal = prod.getPrecio() * p.getCantidad();
			return new PedidoResumenDTO(p.getId(), p.getFecha(), u.getId(), prod.getId(), prod.getNombre(),
					prod.getMarca(), prod.getPrecio(), p.getCantidad(), subtotal);
		}).toList();
	}

	// NUEVO: Detalle de un pedido mío con info del producto
	public PedidoResumenDTO detalleMiPedidoResumen(Long pedidoId) {
		if (pedidoId == null)
			throw new RuntimeException("pedidoId es obligatorio");

		Usuario u = currentUser();

		Pedido p = pedidoRepository.findById(pedidoId).orElseThrow(() -> new RuntimeException("Pedido no existe"));

		if (!p.getUsuario().getId().equals(u.getId())) {
			throw new RuntimeException("No puedes ver un pedido que no es tuyo");
		}

		Producto prod = p.getProducto();
		double subtotal = prod.getPrecio() * p.getCantidad();

		return new PedidoResumenDTO(p.getId(), p.getFecha(), u.getId(), prod.getId(), prod.getNombre(), prod.getMarca(),
				prod.getPrecio(), p.getCantidad(), subtotal);
	}

	private Usuario currentUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return usuarioRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado en BD"));
	}
}
