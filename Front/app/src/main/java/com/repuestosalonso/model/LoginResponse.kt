package com.repuestosalonso.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("userId") val userId: Long,
)

//    val success: Boolean,
//    val message: String,
//    val token: String?,
//    val usuario: Usuario?


