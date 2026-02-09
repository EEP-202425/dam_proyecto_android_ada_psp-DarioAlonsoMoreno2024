package com.proyectofinal.services;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.proyectofinal.dao.CarritoItemRepository;
import com.proyectofinal.dao.ProductRepository;
import com.proyectofinal.dao.UsuarioRepository;
import com.proyectofinal.dominio.CarritoItem;
import com.proyectofinal.dominio.Producto;
import com.proyectofinal.dominio.Usuario;
import com.proyectofinal.dto.CarritoItemResponse;
import com.proyectofinal.dto.CarritoResponse;

@Service
public class CarritoService {

	private final CarritoItemRepository carritoRepo;
	private final ProductRepository productoRepo;
	private final UsuarioRepository usuarioRepo;

	public CarritoService(CarritoItemRepository carritoRepo, ProductRepository productoRepo,
			UsuarioRepository usuarioRepo) {
		this.carritoRepo = carritoRepo;
		this.productoRepo = productoRepo;
		this.usuarioRepo = usuarioRepo;
	}

	public CarritoResponse verCarrito() {
		Usuario u = currentUser();
		List<CarritoItem> items = carritoRepo.findByUsuario_Id(u.getId());

		List<CarritoItemResponse> resp = items.stream().map(ci -> {
			Producto p = ci.getProducto();
			double subtotal = p.getPrecio() * ci.getCantidad();
			return new CarritoItemResponse(ci.getId(), p.getId(), p.getNombre(), p.getMarca(), p.getPrecio(),
					ci.getCantidad(), subtotal);
		}).toList();

		double total = resp.stream().mapToDouble(CarritoItemResponse::getSubtotal).sum();
		return new CarritoResponse(resp, total);
	}

	public CarritoResponse addProducto(Long productoId, int cantidad) {
		if (productoId == null)
			throw new RuntimeException("productoId es obligatorio");
		if (cantidad <= 0)
			throw new RuntimeException("cantidad debe ser > 0");

		Usuario u = currentUser();
		Producto p = productoRepo.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no existe"));

		// Nota: aquí NO descontamos stock. Eso se hará al confirmar pedido.
		CarritoItem item = carritoRepo.findByUsuario_IdAndProducto_Id(u.getId(), p.getId())
				.orElseGet(() -> new CarritoItem(u, p, 0));

		item.setCantidad(item.getCantidad() + cantidad);
		carritoRepo.save(item);

		return verCarrito();
	}

	public CarritoResponse updateCantidad(Long itemId, int cantidad) {
		if (itemId == null)
			throw new RuntimeException("itemId es obligatorio");
		if (cantidad <= 0)
			throw new RuntimeException("cantidad debe ser > 0");

		Usuario u = currentUser();
		CarritoItem item = carritoRepo.findById(itemId).orElseThrow(() -> new RuntimeException("Item no existe"));

		if (!item.getUsuario().getId().equals(u.getId())) {
			throw new RuntimeException("No puedes modificar un carrito que no es tuyo");
		}

		item.setCantidad(cantidad);
		carritoRepo.save(item);
		return verCarrito();
	}

	public CarritoResponse deleteItem(Long itemId) {
		if (itemId == null)
			throw new RuntimeException("itemId es obligatorio");

		Usuario u = currentUser();
		CarritoItem item = carritoRepo.findById(itemId).orElseThrow(() -> new RuntimeException("Item no existe"));

		if (!item.getUsuario().getId().equals(u.getId())) {
			throw new RuntimeException("No puedes borrar un item que no es tuyo");
		}

		carritoRepo.delete(item);
		return verCarrito();
	}

	public void vaciarCarrito() {
		Usuario u = currentUser();
		carritoRepo.deleteByUsuario_Id(u.getId());
	}

	private Usuario currentUser() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return usuarioRepo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado en BD"));
	}
}
