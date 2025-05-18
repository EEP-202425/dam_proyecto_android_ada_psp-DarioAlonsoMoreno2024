package com.repuestosalonso.viewmodel

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
