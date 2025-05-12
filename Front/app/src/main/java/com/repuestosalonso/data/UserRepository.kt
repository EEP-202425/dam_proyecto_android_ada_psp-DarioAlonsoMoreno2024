package com.repuestosalonso.data

import com.repuestosalonso.model.LoginRequest
import com.repuestosalonso.model.LoginResponse
import com.repuestosalonso.network.ApiService
import retrofit2.Response

class UserRepository(private val api: ApiService) {
    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return api.login(LoginRequest(email, password))
    }
}
