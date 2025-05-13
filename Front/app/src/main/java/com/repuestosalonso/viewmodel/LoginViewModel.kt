//package com.repuestosalonso.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.repuestosalonso.data.Repository
//import com.repuestosalonso.model.LoginRequest
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class LoginViewModel(private val repository: Repository) : ViewModel() {
//
//    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
//    val loginState: StateFlow<LoginState> get() = _loginState
//
//    fun loginUser(email: String, password: String) {
//        viewModelScope.launch {
//            _loginState.value = LoginState.Loading
//            try {
//                val response = repository.loginUser(email, password)
//                if (response.isSuccessful) {
//                    _loginState.value = LoginState.Success(response.body())
//                } else {
//                    _loginState.value = LoginState.Error("Credenciales incorrectas")
//                }
//            } catch (e: Exception) {
//                _loginState.value = LoginState.Error("Error: ${e.message}")
//            }
//        }
//    }
//}
