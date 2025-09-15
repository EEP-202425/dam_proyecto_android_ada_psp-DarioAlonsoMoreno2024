
package com.repuestosalonso.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.repuestosalonso.data.Repository
import com.repuestosalonso.model.LoginResponse
import com.repuestosalonso.ui.theme.RepuestosAlonsoTheme
import com.repuestosalonso.viewmodel.UserViewModel
import com.repuestosalonso.viewmodel.UserViewModelFactory
import com.repuestosalonso.viewmodel.UiState
import androidx.compose.runtime.livedata.observeAsState

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val factory = UserViewModelFactory(repository)

        val userViewModel: UserViewModel by viewModels { factory }


        @Composable
        fun LoginScreen(
            viewModel: UserViewModel,
            onLoginSuccess: () -> Unit
        ) {
            // Estado de email y password
            var email by remember { mutableStateOf("test@example.com") }
            var password by remember { mutableStateOf("password") }

            // Observamos el estado de login
            val loginState by viewModel.loginState.observeAsState(UiState.Loading)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Campo Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Campo Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Botón de login
                Button(
                    onClick = { viewModel.loginUser(email, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar estado de carga, éxito o error
                when (val state = loginState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator()
                    }
                    is UiState.Success -> {
                        Text("Login Success: ${(state.data as LoginResponse).token}")
                        // Llamar onLoginSuccess o navegar
                        LaunchedEffect(state) {
                            onLoginSuccess()
                        }
                    }
                    is UiState.Error -> {
                        Text("Error: ${state.error}")
                    }
                    UiState.Idle       -> { /* nothing */ }

                }

            }

        setContent {
            RepuestosAlonsoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamamos al composable LoginScreen con UserViewModel
                    LoginScreen(
                        viewModel = userViewModel,
                        onLoginSuccess = {
                            // Aquí navegas si el login tiene éxito
                        }
                    )
                }
            }
        }
    }
}
    }











//package com.repuestosalonso.ui
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextField
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.repuestosalonso.data.Repository
//import com.repuestosalonso.viewmodel.UiState
//import com.repuestosalonso.viewmodel.UserViewModel
//
//
//class LoginActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val repository = Repository()
//        val factory = LoginViewModelFactory(repository)
//
//        setContent {
//            val viewModel: LoginViewModel = viewModel(factory = factory)
//
//            LoginScreen(viewModel = viewModel) {
//                // Aquí navegas si el login tiene éxito
//            }
//        }
//    }
//}
//
//
//
//@Composable
//fun LoginScreen(viewModel: UserViewModel) {
//    val loginState by viewModel.loginState
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        // Formulario de login
//        var email = "test@example.com"
//        var password = "password"
//
//        TextField(
//            value = email,
//            onValueChange = { email = it },
//            label = { Text("Email") }
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        TextField(
//            value = password,
//            onValueChange = { password = it },
//            label = { Text("Password") }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Botón de login
//        Button(
//            onClick = { viewModel.loginUser(email, password) },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Login")
//        }
//
//        // Mostrar estado de carga, éxito o error
//        when (val state = loginState) {
//            is UiState.Loading -> {
//                CircularProgressIndicator()
//            }
//            is UiState.Success -> {
//                Text("Login Success: ${state.data.token}")
//            }
//            is UiState.Error -> {
//                Text("Error: ${state.error}")
//            }
//        }
//    }
//}
//
