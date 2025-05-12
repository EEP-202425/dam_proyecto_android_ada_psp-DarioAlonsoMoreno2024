package com.repuestosalonso.model

import com.google.gson.annotations.SerializedName

data class Repuesto(
    @SerializedName("id")    val id: Long,
    @SerializedName("userId")val userId: Long,    // si el JSON usa userId
    @SerializedName("precio")val precio: Double,
    @SerializedName("model") val model: String,
    @SerializedName("year")  val year: Int
)
