package com.repuestosalonso.model

import com.google.gson.annotations.SerializedName

data class NewRepuestoRequest(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("precio") val precio: Double,
    @SerializedName("year")   val year: Int,
    @SerializedName("usuarioId") val usuarioId: Long
)
