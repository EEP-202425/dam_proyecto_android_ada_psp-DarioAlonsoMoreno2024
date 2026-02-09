package com.proyectofinal.dto;

import java.util.List;

public class ConfirmPedidoResponse {

	private List<PedidoResponse> pedidos;
	private double total;
	private int items;

	public ConfirmPedidoResponse(List<PedidoResponse> pedidos, double total, int items) {
		this.pedidos = pedidos;
		this.total = total;
		this.items = items;
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
