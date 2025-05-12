package com.repuestosalonso.data

import com.repuestosalonso.model.Repuesto
import com.repuestosalonso.network.RetrofitClient
import com.repuestosalonso.network.RetrofitClient.apiService
import retrofit2.Response

class RepuestosRepository {
    private val api = RetrofitClient.apiService

    suspend fun fetchRepuestos(token: String): Response<List<Repuesto>> {
        return api.getRepuestos("Bearer $token")
    }
}