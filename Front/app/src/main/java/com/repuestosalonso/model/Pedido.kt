package com.repuestosalonso.model

import com.repuestosalonso.model.ItemPedido

data class Pedido(
    val id: Int = 0,
    val userId: Int,
    val fecha: String,
    val estado: String,
    val items: List<ItemPedido> = emptyList(),
    val total: Double = 0.0
)
