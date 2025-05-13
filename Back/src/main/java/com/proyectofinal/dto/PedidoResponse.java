package com.proyectofinal.dto;

import java.time.LocalDate;

public class PedidoResponse {
	private Long id;
	private LocalDate fecha;
	private Long usuarioId;
	private Long productoId;
	private Integer cantidad;

	public PedidoResponse(Long id, LocalDate fecha, Long usuarioId, Long productoId) {
		this.id = id;
		this.fecha = fecha;
		this.usuarioId = usuarioId;
		this.productoId = productoId;
	}

	public Long getId() {
		return id;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public Long getProductoId() {
		return productoId;
	}
}
