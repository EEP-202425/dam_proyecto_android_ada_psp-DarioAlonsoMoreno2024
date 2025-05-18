package com.repuestosalonso.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repuestosalonso.data.RepuestosRepository
import com.repuestosalonso.model.PedidoResponse
import com.repuestosalonso.model.Repuesto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RepuestosViewModel(
    private val repository: RepuestosRepository
) : ViewModel() {

    private val _repuestos = MutableStateFlow<List<Repuesto>>(emptyList())
    val repuestos: StateFlow<List<Repuesto>> = _repuestos


    fun loadRepuestos(token: String) {
        viewModelScope.launch {
            val resp = repository.fetchRepuestos(token)
            if (resp.isSuccessful) {
                resp.body()?.let { lista ->
                    _repuestos.value = lista
                }
            }
        }
    }


    /** Eliminar un repuesto tanto de la BD como de la lista local */
    fun deleteProduct(token: String, productId: Long) {
        viewModelScope.launch {
            val resp = repository.deleteProduct(token, productId)
            if (resp.isSuccessful) {
                _repuestos.value = _repuestos.value.filterNot { it.id == productId }
            }
        }
    }

    suspend fun createRepuesto(
        token: String,
        userId: Long,
        nombre: String,
        precio: Double,
        year: Int
    ): Response<Repuesto> {
        val resp = repository.addRepuesto(token, userId, nombre, precio, year)
        if (resp.isSuccessful) {
            resp.body()?.let { nuevo ->
                _repuestos.value = listOf(nuevo) + _repuestos.value
            }
        }
        return resp
    }

    suspend fun createOrder(
        token: String,
        userId: Long,
        productId: Long,
        count: Int
    ): Response<PedidoResponse> {
        return repository.makeOrder(token, userId, productId, count)
    }


    /** Actualizar un repuesto existente */
    fun updateRepuesto(
        token: String,
        userId: Long,
        productId: Long,
        nombre: String,
        precio: Double,
        year: Int
    ) {
        viewModelScope.launch {
            val resp = repository.updateRepuesto(token, userId, productId, nombre, precio, year)
            if (resp.isSuccessful) {
                resp.body()?.let { actualizado ->
                    _repuestos.value = _repuestos.value.map {
                        if (it.id == productId) actualizado else it
                    }
                }
            }
        }
    }
}
