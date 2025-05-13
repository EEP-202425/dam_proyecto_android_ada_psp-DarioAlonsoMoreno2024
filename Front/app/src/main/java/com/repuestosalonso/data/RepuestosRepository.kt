package com.repuestosalonso.data

import android.util.Log
import com.repuestosalonso.model.NewRepuestoRequest
import com.repuestosalonso.model.PedidoRequest
import com.repuestosalonso.model.PedidoResponse
import com.repuestosalonso.model.Repuesto
import com.repuestosalonso.network.RetrofitClient
import retrofit2.Response

class RepuestosRepository {
    private val api = RetrofitClient.apiService

    suspend fun fetchRepuestos(token: String): Response<List<Repuesto>> {
        return api.getRepuestos("Bearer $token")
    }

    suspend fun deleteProduct(token: String, productId: Long): Response<Unit> {
        return api.deleteProduct("Bearer $token", productId)
    }

    suspend fun makeOrder(
        token: String,
        userId: Long,
        productId: Long,
        count: Int
    ): Response<PedidoResponse> {
        val request = PedidoRequest(
            userId =  userId,
            productId = productId,
            count  = count
        )

        Log.d("ORDER-REQ", "REQUEST JSON: $request")

      return api.crearPedido("Bearer $token", request)
    }
    suspend fun addRepuesto(
        token: String,
        userId: Long,
        nombre: String,
        precio: Double,
        year: Int
    ): Response<Repuesto> {
        val request = NewRepuestoRequest(nombre, precio, year, userId)
        return api.crearRepuesto("Bearer $token", request)
    }

}
