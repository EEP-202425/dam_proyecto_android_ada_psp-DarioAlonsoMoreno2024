package com.proyectofinal.dto;

public class CarritoAddRequest {

	private Long productoId;
	private Integer cantidad = 1;

	public CarritoAddRequest() {
	}

	public Long getProductoId() {
		return productoId;
	}

	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
