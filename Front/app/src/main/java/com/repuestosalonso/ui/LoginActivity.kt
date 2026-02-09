package com.repuestosalonso.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.repuestosalonso.MainActivity
import com.repuestosalonso.data.Repository
import com.repuestosalonso.model.LoginResponse
import com.repuestosalonso.network.TokenManager
import com.repuestosalonso.ui.theme.RepuestosAlonsoTheme
import com.repuestosalonso.viewmodel.UiState
import com.repuestosalonso.viewmodel.UserViewModel
import com.repuestosalonso.viewmodel.UserViewModelFactory

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository()
        val factory = UserViewModelFactory(repository)
        val userViewModel: UserViewModel by viewModels { factory }

        setContent {
            RepuestosAlonsoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        viewModel = userViewModel,
                        onLoginSuccess = { token ->
                            // 1) Guardar JWT
                            TokenManager.saveToken(this@LoginActivity, token)

                            // 2) Ir a la pantalla principal (tu actividad de repuestos)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)

                            // 3) Cerrar login para que "Explain back" no vuelva aquí
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: UserViewModel,
    onLoginSuccess: (token: String) -> Unit
) {
    var email by remember { mutableStateOf("test@example.com") }
    var password by remember { mutableStateOf("password") }

    val loginState by viewModel.loginState.observeAsState(UiState.Idle)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.loginUser(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = loginState) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }

            is UiState.Success -> {
                val token = (state.data as LoginResponse).token

                Text("Login OK")

                // IMPORTANTE: solo ejecutar una vez
                LaunchedEffect(token) {
                    onLoginSuccess(token)
                }
            }

            is UiState.Error -> {
                Text("Error: ${state.error}")
            }

            UiState.Idle -> {
                // No hacemos nada
            }
        }
    }
}
