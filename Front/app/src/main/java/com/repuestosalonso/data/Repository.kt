package com.repuestosalonso.data

import android.content.Context
import com.repuestosalonso.model.LoginRequest
import com.repuestosalonso.model.LoginResponse
import com.repuestosalonso.network.RetrofitClient
import retrofit2.Response

class Repository(
    private val context: Context
) {
    private val api = RetrofitClient.apiService(context)

    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        val loginRequest = LoginRequest(email, password)
        return api.login(loginRequest)
    }
}
