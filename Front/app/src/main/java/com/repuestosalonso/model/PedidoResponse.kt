package com.repuestosalonso.model

import com.google.gson.annotations.SerializedName

data class PedidoResponse(
    @SerializedName("id")          val id: Long,
    @SerializedName("fecha")       val fecha: String,    // o el formato que devuelva tu API
    @SerializedName("userId")   val userId: Long,
    @SerializedName("productId")  val productId: Long,
    @SerializedName("count")    val count: Int
)
