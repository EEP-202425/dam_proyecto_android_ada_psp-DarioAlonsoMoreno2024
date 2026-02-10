package com.repuestosalonso.model

data class NewRepuestoRequest(
    val nombre: String,
    val precio: Double,
    val year: Int,
    val usuarioId: Long,
    val marca: String,
    val stock: Int
)
