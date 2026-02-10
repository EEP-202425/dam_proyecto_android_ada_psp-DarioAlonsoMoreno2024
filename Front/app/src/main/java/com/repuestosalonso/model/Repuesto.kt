package com.repuestosalonso.model

data class Repuesto(
    val id: Long,
    val userId: Long,
    val precio: Double,
    val nombre: String,
    val year: Int,
    val marca: String,
    val stock: Int
)

