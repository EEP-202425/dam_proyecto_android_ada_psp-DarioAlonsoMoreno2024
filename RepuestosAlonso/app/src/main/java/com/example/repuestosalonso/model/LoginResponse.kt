package com.example.repuestosalonso.model

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String?,
    val usuario: Usuario?
)