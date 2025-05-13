package com.repuestosalonso.model

import com.google.gson.annotations.SerializedName

data class PedidoRequest(
    @SerializedName("userId")  val userId: Long,
    @SerializedName("productId") val productId: Long,
    @SerializedName("count")   val count: Int
)
