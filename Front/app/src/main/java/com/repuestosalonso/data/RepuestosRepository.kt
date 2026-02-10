package com.repuestosalonso.data

import android.content.Context
import android.util.Log
import com.repuestosalonso.model.NewRepuestoRequest
import com.repuestosalonso.model.PedidoRequest
import com.repuestosalonso.model.PedidoResponse
import com.repuestosalonso.model.Repuesto
import com.repuestosalonso.network.RetrofitClient
import retrofit2.Response

class RepuestosRepository(
    private val context: Context
) {
    private val api = RetrofitClient.apiService(context)

    suspend fun fetchRepuestos(): Response<List<Repuesto>> {
        return api.getRepuestos()
    }

    suspend fun deleteProduct(productId: Long): Response<Unit> {
        return api.deleteProduct(productId)
    }

    suspend fun makeOrder(
        userId: Long,
        productId: Long,
        count: Int
    ): Response<PedidoResponse> {
        val request = PedidoRequest(userId = userId, productId = productId, count = count)
        Log.d("ORDER-REQ", "REQUEST JSON: $request")
        return api.crearPedido(request)
    }

    suspend fun addRepuesto(
        userId: Long,
        nombre: String,
        precio: Double,
        year: Int,
        marca: String,
        stock: Int
    ): Response<Repuesto> {
        val request = NewRepuestoRequest(
            nombre = nombre,
            precio = precio,
            year = year,
            usuarioId = userId,
            marca = marca,
            stock = stock
        )
        return api.crearRepuesto(request)
    }

    suspend fun updateRepuesto(
        userId: Long,
        productId: Long,
        nombre: String,
        precio: Double,
        year: Int,
        marca: String,
        stock: Int
    ): Response<Repuesto> {
        val request = NewRepuestoRequest(
            nombre = nombre,
            precio = precio,
            year = year,
            usuarioId = userId,
            marca = marca,
            stock = stock
        )
        return api.updateRepuesto(productId, request)
    }
}
