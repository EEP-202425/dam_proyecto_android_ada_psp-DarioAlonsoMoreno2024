package com.repuestosalonso.network

import com.repuestosalonso.model.LoginRequest
import com.repuestosalonso.model.LoginResponse
import com.repuestosalonso.model.NewRepuestoRequest
import com.repuestosalonso.model.PedidoRequest
import com.repuestosalonso.model.PedidoResponse
import com.repuestosalonso.model.Repuesto
import com.repuestosalonso.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    // Login
    @POST("api/usuarios/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("api/producto")
    suspend fun getRepuestos(
        @Header("Authorization") bearerToken: String
    ): Response<List<Repuesto>>

    @POST("api/pedidos/crear")
     suspend fun crearPedido(
         @Header("Authorization") bearerToken: String,
     @Body pedido: PedidoRequest
     ): Response<PedidoResponse>

    // Crear un nuevo repuesto
    @POST("api/producto")
    suspend fun crearRepuesto(
        @Header("Authorization") bearer: String,
        @Body request: NewRepuestoRequest
    ): Response<Repuesto>


    @DELETE("api/producto/{id}")
    suspend fun deleteProduct(
        @Header("Authorization") bearerToken: String,
        @Path("id") id: Long
    ): Response<Unit>

    @PUT("api/producto/{id}")
    suspend fun updateRepuesto(
        @Header("Authorization") bearer: String,
        @Path("id") id: Long,
        @Body request: NewRepuestoRequest
    ): Response<Repuesto>

    // Listar todos los usuarios (si lo necesitas)
//    @GET("api/usuarios")
//    suspend fun getUsers(): Response<List<Usuario>>

    // Crear usuario
//    @POST("api/usuarios/registro")
//    suspend fun crearUsuario(@Body usuario: Usuario): Response<Usuario>

}
