package com.example.repuestosalonso.model

data class Repuesto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val disponible: Boolean,
    val imagen: String?
)