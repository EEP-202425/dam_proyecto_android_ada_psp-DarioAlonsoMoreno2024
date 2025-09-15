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

