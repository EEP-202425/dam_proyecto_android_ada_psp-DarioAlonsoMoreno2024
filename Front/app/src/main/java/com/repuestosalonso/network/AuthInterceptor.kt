package com.repuestosalonso.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        // Endpoints públicos (sin token)
        val path = original.url.encodedPath
        val isPublic =
            path.endsWith("/api/usuarios/login") ||
                    path.endsWith("/api/usuarios/registro")

        if (isPublic) {
            return chain.proceed(original)
        }

        val token = TokenManager.getToken(context)
        val requestWithAuth = if (!token.isNullOrBlank()) {
            original.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            original
        }

        return chain.proceed(requestWithAuth)
    }
}