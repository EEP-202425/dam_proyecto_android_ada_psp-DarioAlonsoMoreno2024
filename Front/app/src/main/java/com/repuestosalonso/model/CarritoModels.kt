package com.repuestosalonso.model

data class CarritoAddRequest(
    val productoId: Long,
    val cantidad: Int = 1
)

data class CarritoUpdateRequest(
    val cantidad: Int
)

data class CarritoItemResponse(
    val itemId: Long,
    val productoId: Long,
    val nombre: String,
    val marca: String,
    val precio: Double,
    val cantidad: Int,
    val subtotal: Double
)

data class CarritoResponse(
    val items: List<CarritoItemResponse>,
    val total: Double
)
