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

    fun loadRepuestos() {
        viewModelScope.launch {
            val resp = repository.fetchRepuestos()
            if (resp.isSuccessful) {
                _repuestos.value = resp.body().orEmpty()
            }
        }
    }

    fun deleteProduct(productId: Long) {
        viewModelScope.launch {
            val resp = repository.deleteProduct(productId)
            if (resp.isSuccessful) {
                _repuestos.value = _repuestos.value.filterNot { it.id == productId }
            }
        }
    }

    suspend fun createRepuesto(
        userId: Long,
        nombre: String,
        precio: Double,
        year: Int,
        marca: String,
        stock: Int
    ): Response<Repuesto> {
        val resp = repository.addRepuesto(userId, nombre, precio, year, marca, stock)
        if (resp.isSuccessful) {
            resp.body()?.let { nuevo ->
                _repuestos.value = listOf(nuevo) + _repuestos.value
            }
        }
        return resp
    }

    suspend fun createOrder(
        userId: Long,
        productId: Long,
        count: Int
    ): Response<PedidoResponse> {
        return repository.makeOrder(userId, productId, count)
    }

    fun updateRepuesto(
        userId: Long,
        productId: Long,
        nombre: String,
        precio: Double,
        year: Int,
        marca: String,
        stock: Int
    ) {
        viewModelScope.launch {
            val resp = repository.updateRepuesto(userId, productId, nombre, precio, year, marca, stock)
            if (resp.isSuccessful) {
                resp.body()?.let { actualizado ->
                    _repuestos.value = _repuestos.value.map { if (it.id == productId) actualizado else it }
                }
            }
        }
    }
}
