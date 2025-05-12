// app/src/main/java/com/repuestosalonso/model/PedidoRequest.kt
package com.repuestosalonso.model

import com.google.gson.annotations.SerializedName

data class PedidoRequest(
    @SerializedName("productoId") val productoId: Long
)
