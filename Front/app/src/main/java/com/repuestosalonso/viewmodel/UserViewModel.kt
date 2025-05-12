package com.repuestosalonso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repuestosalonso.data.Repository
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle    : UiState()
    object Loading : UiState()
    data class Success(val data: Any) : UiState()
    data class Error(val error: String) : UiState()
}

class UserViewModel(private val repository: Repository) : ViewModel() {
    private val _loginState = MutableLiveData<UiState>()
    val loginState: LiveData<UiState> = _loginState

    fun loginUser(email: String, password: String) {
        _loginState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val response = repository.loginUser(email, password)
            //    Log.d("LoginVM", "Código HTTP: ${resp.code()}, Body: ${resp.body()}")

                if (response.isSuccessful && response.body() != null) {
                    _loginState.value = UiState.Success(response.body()!!)
                } else {
                    _loginState.value = UiState.Error("Login fallido: ${response.message()}")
                }
            } catch (e: Exception) {
                _loginState.value = UiState.Error("Exception: ${e.localizedMessage}")
            }
        }
    }
}



//class UserViewModel(private val repository: Repository) : ViewModel() {
//
//    private val _loginState = mutableStateOf<UiState<LoginResponse>>(UiState.Loading)
//    val loginState: State<UiState<LoginResponse>> = _loginState
//
//    fun loginUser(email: String, password: String) {
//        _loginState.value = UiState.Loading
//        viewModelScope.launch {
//            try {
//                val response = repository.loginUser(email, password)
//                if (response.isSuccessful && response.body() != null) {
//                    _loginState.value = UiState.Success(response.body()!!)
//                } else {
//                    _loginState.value = UiState.Error("Login fallido: ${response.message()}")
//                }
//            } catch (e: Exception) {
//                _loginState.value = UiState.Error("Error: ${e.message}")
//            }
//        }
//    }
//}


//// Función para obtener el usuario
//    fun getUser() {
//        _userState.value = UiState.Loading
//        viewModelScope.launch {
//            try {
//                val response: Response<List<Usuario>> = repository.getUser()
//                if (response.isSuccessful && response.body() != null) {
//                    _userState.value = UiState.Success(response.body()!!)
//                } else {
//                    _userState.value = UiState.Error("Failed to get user data")
//                }
//            } catch (e: Exception) {
//                _userState.value = UiState.Error("Error: ${e.localizedMessage}")
//            }
//        }
//    }
