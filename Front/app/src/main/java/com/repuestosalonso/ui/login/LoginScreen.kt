package com.repuestosalonso.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.repuestosalonso.model.LoginResponse
import com.repuestosalonso.network.TokenManager
import com.repuestosalonso.viewmodel.UiState
import com.repuestosalonso.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    viewModel: UserViewModel,
    onLoginSuccess: (token: String, userId: Long) -> Unit
) {
    val context = LocalContext.current

    // Empezamos en Idle para no mostrar spinner hasta que lancemos el login
    val state by viewModel.loginState.observeAsState(initial = UiState.Idle)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { viewModel.loginUser(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(Modifier.height(16.dp))

        when (state) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Error -> {
                Text(
                    text = (state as UiState.Error).error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }

            is UiState.Success -> {
                LaunchedEffect(state) {
                    val resp = (state as UiState.Success).data as LoginResponse

                    // ✅ CLAVE: guardamos el JWT para que AuthInterceptor lo añada en POST/PUT/DELETE
                    TokenManager.saveToken(context, resp.token)

                    // Navegación
                    onLoginSuccess(resp.token, resp.userId)
                }
            }

            UiState.Idle -> {
                // nada
            }
        }
    }
}
