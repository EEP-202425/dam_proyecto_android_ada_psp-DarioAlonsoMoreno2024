package com.proyectofinal.dto;

import java.util.List;

public class ConfirmPedidoResponse {

	private String orderNumber; // id visual (no BD)
	private List<PedidoResponse> pedidos;
	private double total;
	private int items;

	public ConfirmPedidoResponse(String orderNumber, List<PedidoResponse> pedidos, double total, int items) {
		this.orderNumber = orderNumber;
		this.pedidos = pedidos;
		this.total = total;
		this.items = items;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public List<PedidoResponse> getPedidos() {
		return pedidos;
	}

	public double getTotal() {
		return total;
	}

	public int getItems() {
		return items;
	}
}