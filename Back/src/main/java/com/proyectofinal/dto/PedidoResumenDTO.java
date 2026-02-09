package com.proyectofinal.dto;

import java.time.LocalDate;

public class PedidoResumenDTO {

	private Long id;
	private LocalDate fecha;

	private Long usuarioId;

	private Long productoId;
	private String productoNombre;
	private String productoMarca;

	private double precioUnitario;
	private int cantidad;
	private double subtotal;

	public PedidoResumenDTO(Long id, LocalDate fecha, Long usuarioId, Long productoId, String productoNombre,
			String productoMarca, double precioUnitario, int cantidad, double subtotal) {
		this.id = id;
		this.fecha = fecha;
		this.usuarioId = usuarioId;
		this.productoId = productoId;
		this.productoNombre = productoNombre;
		this.productoMarca = productoMarca;
		this.precioUnitario = precioUnitario;
		this.cantidad = cantidad;
		this.subtotal = subtotal;
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

	public String getProductoNombre() {
		return productoNombre;
	}

	public String getProductoMarca() {
		return productoMarca;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public int getCantidad() {
		return cantidad;
	}

	public double getSubtotal() {
		return subtotal;
	}
}
