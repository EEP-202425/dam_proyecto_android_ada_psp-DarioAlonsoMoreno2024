//package com.repuestosalonso.viewmodel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.repuestosalonso.data.Repository
//
//class LoginViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return LoginViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}


package com.repuestosalonso.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.repuestosalonso.data.Repository

class UserViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
