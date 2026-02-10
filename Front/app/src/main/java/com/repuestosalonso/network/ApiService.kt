package com.repuestosalonso.network

import com.repuestosalonso.model.LoginRequest
import com.repuestosalonso.model.LoginResponse
import com.repuestosalonso.model.NewRepuestoRequest
import com.repuestosalonso.model.PedidoRequest
import com.repuestosalonso.model.PedidoResponse
import com.repuestosalonso.model.Repuesto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("api/usuarios/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/producto")
    suspend fun getRepuestos(): Response<List<Repuesto>>

    @POST("api/pedidos/crear")
    suspend fun crearPedido(@Body pedido: PedidoRequest): Response<PedidoResponse>

    @POST("api/producto")
    suspend fun crearRepuesto(@Body request: NewRepuestoRequest): Response<Repuesto>

    @DELETE("api/producto/{id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<Unit>

    @PUT("api/producto/{id}")
    suspend fun updateRepuesto(
        @Path("id") id: Long,
        @Body request: NewRepuestoRequest
    ): Response<Repuesto>
}
