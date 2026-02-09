package com.repuestosalonso.model

data class UserMeResponse (
    val id: Long,
    val email: String,
    val name: String?,
    val lastName: String?
)

