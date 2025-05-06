package com.example.repuestosalonso.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.repuestosalonso.network.RetrofitClient
import com.example.repuestosalonso.model.LoginRequest
import com.example.repuestosalonso.R
import com.example.repuestosalonso.ui.theme.RepuestosAlonsoTheme
import com.example.repuestosalonso.uii.HomeScreen
//import com.example.repuestosalonso.uii.screens
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepuestosAlonsoTheme {
                HomeScreen()
            }
        }
    }
}

//            // Encontrar las vistas por ID
//            val usernameEditText =
//                findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.usernameEditText)
//            val passwordEditText =
//                findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.passwordEditText)
//            val loginButton = findViewById<android.widget.Button>(R.id.loginButton)
//            val progressBar = findViewById<android.widget.ProgressBar>(R.id.progressBar)
//
//            // Verificar si ya hay un token guardado
//            val sharedPref = getSharedPreferences("RepuestosApp", MODE_PRIVATE)
//            val token = sharedPref.getString("token", null)
//            if (!token.isNullOrEmpty()) {
//                navigateToRepuestos()
//                return@setContent
//            }
//
//            loginButton.setOnClickListener {
//                val username = usernameEditText.text.toString()
//                val password = passwordEditText.text.toString()
//
//                if (username.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
//                        .show()
//                    return@setOnClickListener
//                }
//
//                login(username, password, progressBar, loginButton)
//            }
//        }
//
//        fun login(
//            username: String,
//            password: String,
//            progressBar: android.widget.ProgressBar,
//            loginButton: android.widget.Button
//        ) {
//            progressBar.visibility = View.VISIBLE
//            loginButton.isEnabled = false
//
//            lifecycleScope.launch {
//                try {
//                    val loginRequest = LoginRequest(username, password)
//                    val response = RetrofitClient.apiService.login(loginRequest)
//
//                    if (response.success && response.token != null) {
//                        // Guardar el token
//                        val sharedPref = getSharedPreferences("RepuestosApp", MODE_PRIVATE)
//                        with(sharedPref.edit()) {
//                            putString("token", response.token)
//                            putInt("userId", response.usuario?.id ?: 0)
//                            putString("username", response.usuario?.username ?: "")
//                            apply()
//                        }
//
//                        navigateToRepuestos()
//                    } else {
//                        Toast.makeText(
//                            this@MainActivity,
//                            response.message, Toast.LENGTH_LONG
//                        ).show()
//                    }
//                } catch (e: Exception) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Error: ${e.message}", Toast.LENGTH_LONG
//                    ).show()
//                } finally {
//                    progressBar.visibility = View.GONE
//                    loginButton.isEnabled = true
//                }
//            }
//        }
//
//        fun navigateToRepuestos() {
//            val intent = Intent(this, RepuestosActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
//    }