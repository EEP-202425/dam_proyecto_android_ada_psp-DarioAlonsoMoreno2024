package com.repuestosalonso.viewmodel

import androidx.lifecycle.*
import com.repuestosalonso.data.RepuestosRepository
import com.repuestosalonso.model.Repuesto
import kotlinx.coroutines.launch
import retrofit2.Response

// Reutilizamos UiListState de antes
sealed class UiListState {
    object Loading                       : UiListState()
    data class Success(val data: List<Repuesto>) : UiListState()
    data class Error(val message: String)        : UiListState()
}

class RepuestosViewModel(
    private val repo: RepuestosRepository
) : ViewModel() {
    private val _state = MutableLiveData<UiListState>(UiListState.Loading)
    val state: LiveData<UiListState> = _state

    fun loadRepuestos(token: String) {
        _state.value = UiListState.Loading
        viewModelScope.launch {
            try {
                val resp: Response<List<Repuesto>> = repo.fetchRepuestos(token)
                if (resp.isSuccessful) {
                    resp.body()?.let { _state.value = UiListState.Success(it) }
                        ?: run { _state.value = UiListState.Error("Respuesta vacía") }
                } else {
                    _state.value = UiListState.Error("Error HTTP ${resp.code()}")
                }
            } catch (e: Exception) {
                _state.value = UiListState.Error(e.localizedMessage ?: "Error desconocido")
            }
        }
    }
}
