package com.example.repuestosalonso.network

import com.example.repuestosalonso.model.LoginRequest
import com.example.repuestosalonso.model.LoginResponse
import com.example.repuestosalonso.model.Pedido
import com.example.repuestosalonso.model.Repuesto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @GET("repuestos")
    suspend fun getRepuestos(@Header("Authorization") token: String): List<Repuesto>

    @POST("pedidos")
    suspend fun crearPedido(
        @Header("Authorization") token: String,
        @Body pedido: Pedido
    ): Pedido

    @GET("pedidos")
    suspend fun getPedidos(@Header("Authorization") token: String): List<Pedido>
}
