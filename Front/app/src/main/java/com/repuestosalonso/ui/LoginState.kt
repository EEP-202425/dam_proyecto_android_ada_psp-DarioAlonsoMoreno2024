package com.repuestosalonso.ui

import com.repuestosalonso.model.LoginResponse

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val userData: LoginResponse?) : LoginState()
    data class Error(val message: String) : LoginState()
}
