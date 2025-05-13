package com.proyectofinal.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectofinal.dao.PedidoRepository;
import com.proyectofinal.dao.ProductRepository;
import com.proyectofinal.dao.UsuarioRepository;
import com.proyectofinal.dominio.Pedido;
import com.proyectofinal.dominio.Producto;
import com.proyectofinal.dominio.Usuario;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	 @Autowired
	 private ProductRepository productoRepo;

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private InventarioService inventarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	public List<Pedido> obtenerTodos() {
		return pedidoRepository.findAll();
	}

	  public Pedido crearPedido(Long usuarioId, Long productoId, int cantidad) {
		    Usuario u  = usuarioRepo.findById(usuarioId)
		                  .orElseThrow(() -> new RuntimeException("Usuario no existe"));
		    Producto p = productoRepo.findById(productoId)
		                  .orElseThrow(() -> new RuntimeException("Producto no existe"));

		    Pedido pedido = new Pedido();
		    pedido.setUsuario(u);
		    pedido.setProducto(p);
		    pedido.setFecha(LocalDate.now());
		    return pedidoRepository.save(pedido);
		  }
		}
