package com.repuestosalonso.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repuestosalonso.data.RepuestosRepository
import com.repuestosalonso.model.Repuesto
import com.repuestosalonso.model.PedidoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RepuestosViewModel(
    private val repository: RepuestosRepository
) : ViewModel() {

    private val _repuestos = MutableStateFlow<List<Repuesto>>(emptyList())
    val repuestos: StateFlow<List<Repuesto>> = _repuestos

    /** Carga inicial o recarga de repuestos */
    fun loadRepuestos(token: String) {
        viewModelScope.launch {
            val response: Response<List<Repuesto>> = repository.fetchRepuestos(token)
            if (response.isSuccessful) response.body()?.let { lista ->
                _repuestos.value = lista
            }
        }
    }

    /** Crear un nuevo repuesto y añadirlo a la lista */
    suspend fun createRepuesto(
        token: String,
        userId: Long,
        nombre: String,
        precio: Double,
        year: Int
    ): Response<Repuesto> {
        val response = repository.addRepuesto(token, userId, nombre, precio, year)
        if (response.isSuccessful) response.body()?.let { nuevo ->
            val updated = _repuestos.value.toMutableList().apply { add(0, nuevo) }
            _repuestos.value = updated
        }
        return response
    }

    /** Crear un pedido de repuesto */
    suspend fun createOrder(
        token: String,
        userId: Long,
        productId: Long,
        cantidad: Int
    ): Response<PedidoResponse> {
        return repository.makeOrder(token, userId, productId, cantidad)
    }

    fun deleteProduct(token: String, productId: Long) {
        viewModelScope.launch {
            val resp = repository.deleteProduct(token, productId)
            if (resp.isSuccessful) {
                // Filtramos la lista local para quitar el borrado
                _repuestos.value = _repuestos.value.filterNot { it.id == productId }
            }
        }
    }
}
