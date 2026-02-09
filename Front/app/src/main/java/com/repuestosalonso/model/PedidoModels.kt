package com.repuestosalonso.model

data class ConfirmPedidoResponse(
    val orderNumber: String,
    val pedidos: List<PedidoResponse>,
    val total: Double,
    val items: Int
)


data class PedidoResumen(
    val id: Long,
    val fecha: String,
    val usuarioId: Long,
    val productoId: Long,
    val productoNombre: String,
    val productoMarca: String,
    val precioUnitario: Double,
    val cantidad: Int,
    val subtotal: Double
)
