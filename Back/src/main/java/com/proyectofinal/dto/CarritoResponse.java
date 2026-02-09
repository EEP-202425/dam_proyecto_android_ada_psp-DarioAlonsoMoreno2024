package com.proyectofinal.dto;

import java.util.List;

public class CarritoResponse {

	private List<CarritoItemResponse> items;
	private double total;

	public CarritoResponse(List<CarritoItemResponse> items, double total) {
		this.items = items;
		this.total = total;
	}

	public List<CarritoItemResponse> getItems() {
		return items;
	}

	public double getTotal() {
		return total;
	}
}
