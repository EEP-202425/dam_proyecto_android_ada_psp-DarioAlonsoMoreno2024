package com.repuestosalonso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.repuestosalonso.data.RepuestosRepository
import com.repuestosalonso.data.Repository
import com.repuestosalonso.ui.login.LoginScreen
import com.repuestosalonso.ui.ProductsScreen
import com.repuestosalonso.ui.theme.RepuestosAlonsoTheme
import com.repuestosalonso.viewmodel.RepuestosViewModel
import com.repuestosalonso.viewmodel.RepuestosViewModelFactory
import com.repuestosalonso.viewmodel.UserViewModel
import com.repuestosalonso.viewmodel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancia los repositorios
        val userRepo = Repository()
        val repRepo  = RepuestosRepository()

        // Crea factories y ViewModels
        val userVM: UserViewModel by viewModels { UserViewModelFactory(userRepo) }
        val repVM: RepuestosViewModel by viewModels { RepuestosViewModelFactory(repRepo) }

        setContent {
            RepuestosAlonsoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = MaterialTheme.colorScheme.background
                ) {
                    // Crear NavController
                    val navController: NavHostController = rememberNavController()

                    NavHost(
                        navController    = navController,
                        startDestination = "login"
                    ) {


                        composable("login") {
                            LoginScreen(
                                viewModel = userVM,
                                onLoginSuccess = { token ->
                                    navController.navigate("products/$token") {
                                        // Aquí quitamos el inclusive = true
                                        popUpTo("login") { inclusive = false }
                                    }
                                }
                            )
                        }


                        // Pantalla de productos
                        composable(
                            route = "products/{token}",
                            arguments = listOf(navArgument("token") {
                                type = NavType.StringType
                            })
                        ) { backStackEntry ->
                            val token = backStackEntry.arguments?.getString("token") ?: ""
                            // Inicia la carga de repuestos
                            repVM.loadRepuestos(token)
                            // Llama a ProductsScreen con el token, el ViewModel y el NavController
                            ProductsScreen(
                                token         = token,
                                viewModel     = repVM,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
