package com.repuestosalonso.model

data class ItemPedido(
    val id: Int = 0,
    val repuestoId: Int,
    val cantidad: Int,
    val precioUnitario: Double,
    val nombreRepuesto: String,
    val userId: Long
)