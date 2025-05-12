package com.repuestosalonso.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.repuestosalonso.data.RepuestosRepository

class RepuestosViewModelFactory(
    private val repo: RepuestosRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepuestosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepuestosViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
