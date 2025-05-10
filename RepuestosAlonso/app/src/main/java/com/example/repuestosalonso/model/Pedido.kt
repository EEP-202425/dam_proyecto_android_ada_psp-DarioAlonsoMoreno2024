package com.example.repuestosalonso.model

data class Pedido(
    val id: Int = 0,
    val usuarioId: Int,
    val fecha: String,
    val estado: String,
    val items: List<ItemPedido> = emptyList(),
    val total: Double = 0.0
)