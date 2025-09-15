
package com.repuestosalonso.viewmodel

// La clase 'Result' es sellada para manejar los estados de carga, éxito y error.
sealed class Result<out T> {
    // Estado cuando la operación fue exitosa
    data class Success<out T>(val data: T?) : Result<T>()

    // Estado cuando ocurre un error
    data class Error(val message: String?) : Result<Nothing>()

    // Estado cuando la operación está en proceso (cargando)
    data class Loading(val isLoading: Boolean = true) : Result<Nothing>()

    // Métodos auxiliares para crear instancias de los estados
    companion object {
        fun <T> success(data: T?): Result<T> = Success(data)
        fun <T> error(message: String?): Result<T> = Error(message)
        fun <T> loading(isLoading: Boolean = true): Result<T> = Loading(isLoading)
    }
}
