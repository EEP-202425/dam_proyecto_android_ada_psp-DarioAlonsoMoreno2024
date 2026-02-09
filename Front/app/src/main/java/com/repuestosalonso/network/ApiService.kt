package com.repuestosalonso.network

import com.repuestosalonso.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // PUBLICO
    @POST("api/usuarios/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    // PROTEGIDO
    @GET("api/usuarios/me")
    suspend fun me(): Response<UserMeResponse>

    @GET("api/producto")
    suspend fun getRepuestos(): Response<List<Repuesto>>

    @POST("api/producto")
    suspend fun crearRepuesto(@Body request: NewRepuestoRequest): Response<Repuesto>

    @PUT("api/producto/{id}")
    suspend fun updateRepuesto(
        @Path("id") id: Long,
        @Body request: NewRepuestoRequest
    ): Response<Repuesto>

    @DELETE("api/producto/{id}")
    suspend fun deleteProduct(@Path("id") id: Long): Response<Unit>

    // CARRITO
    @GET("api/carrito")
    suspend fun getCarrito(): Response<CarritoResponse>

    @POST("api/carrito/items")
    suspend fun addCarritoItem(@Body request: CarritoAddRequest): Response<CarritoResponse>

    @PUT("api/carrito/items/{itemId}")
    suspend fun updateCarritoItem(
        @Path("itemId") itemId: Long,
        @Body request: CarritoUpdateRequest
    ): Response<CarritoResponse>

    @DELETE("api/carrito/items/{itemId}")
    suspend fun deleteCarritoItem(@Path("itemId") itemId: Long): Response<CarritoResponse>

    @DELETE("api/carrito")
    suspend fun vaciarCarrito(): Response<Unit>

    // PEDIDOS
    @POST("api/pedidos/confirmar")
    suspend fun confirmarPedido(): Response<ConfirmPedidoResponse>

    @GET("api/pedidos/mis")
    suspend fun misPedidos(): Response<List<PedidoResumen>>

    @GET("api/pedidos/mis/{pedidoId}")
    suspend fun detalleMiPedido(@Path("pedidoId") pedidoId: Long): Response<PedidoResumen>
}
