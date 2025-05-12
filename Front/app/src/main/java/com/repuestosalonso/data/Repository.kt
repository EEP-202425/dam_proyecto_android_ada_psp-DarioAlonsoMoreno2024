package com.repuestosalonso.data

import com.repuestosalonso.model.LoginRequest
import com.repuestosalonso.model.LoginResponse
import com.repuestosalonso.network.RetrofitClient
import retrofit2.Response

class Repository {
    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(email, password)
        return RetrofitClient.apiService.login(loginRequest)
    }
}


//    // Función para obtener los datos del usuario
//    suspend fun getUser(): Response<List<Usuario>> {
//        return apiService.getUser()  // Llamada a la API para obtener usuario
//    }
//
//    // Función para crear un usuario
//    suspend fun crearUsuario(usuario: Usuario): Response<Usuario> {
//        return apiService.crearUsuario(usuario)  // Llamada a la API para crear usuario
//    }
//
//    // Función para obtener los repuestos
//    suspend fun getRepuestos(token: String): Response<List<Repuesto>> {
//        return apiService.getRepuestos(token)  // Llamada a la API para obtener repuestos
//    }
//
//    // Función para crear un pedido
//    suspend fun crearPedido(token: String, pedido: Pedido): Response<Pedido> {
//        return apiService.crearPedido(token, pedido)  // Llamada a la API para crear pedido
//    }

