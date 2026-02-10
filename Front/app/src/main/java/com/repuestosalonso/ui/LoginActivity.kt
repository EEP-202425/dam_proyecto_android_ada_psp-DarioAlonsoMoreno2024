package com.repuestosalonso.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.repuestosalonso.data.Repository
import com.repuestosalonso.ui.login.LoginScreen
import com.repuestosalonso.ui.theme.RepuestosAlonsoTheme
import com.repuestosalonso.viewmodel.UserViewModel
import com.repuestosalonso.viewmodel.UserViewModelFactory

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = Repository(applicationContext)
        val userVM: UserViewModel by viewModels { UserViewModelFactory(repository) }

        setContent {
            RepuestosAlonsoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        viewModel = userVM,
                        onLoginSuccess = { token, userId ->
                            // aquí iría navegación por Intent si usaras Activities
                            // pero si ya tienes NavHost en MainActivity, no uses esta Activity.
                        }
                    )
                }
            }
        }
    }
}
