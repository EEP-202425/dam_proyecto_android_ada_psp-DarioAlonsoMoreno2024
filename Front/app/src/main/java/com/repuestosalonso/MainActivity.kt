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
import com.repuestosalonso.network.TokenManager
import com.repuestosalonso.ui.login.AddRepuestoScreen
import com.repuestosalonso.ui.login.LoginScreen
import com.repuestosalonso.ui.login.ProductsScreen
import com.repuestosalonso.ui.theme.RepuestosAlonsoTheme
import com.repuestosalonso.viewmodel.RepuestosViewModel
import com.repuestosalonso.viewmodel.RepuestosViewModelFactory
import com.repuestosalonso.viewmodel.UserViewModel
import com.repuestosalonso.viewmodel.UserViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userRepo = Repository()
        val repRepo  = RepuestosRepository()

        val userVM: UserViewModel by viewModels { UserViewModelFactory(userRepo) }
        val repVM: RepuestosViewModel by viewModels { RepuestosViewModelFactory(repRepo) }

        setContent {
            RepuestosAlonsoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color    = MaterialTheme.colorScheme.background
                ) {
                    val navController: NavHostController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(
                                viewModel = userVM,
                                onLoginSuccess = { token, userId ->
                                    // Guardamos token y dejamos de pasarlo por la URL
                                    TokenManager.saveToken(this@MainActivity, token)

                                    navController.navigate("products/$userId") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // Productos (SIN token en ruta)
                        composable(
                            route = "products/{userId}",
                            arguments = listOf(
                                navArgument("userId") { type = NavType.LongType }
                            )
                        ) { backStack ->
                            val userId = backStack.arguments!!.getLong("userId")

                            ProductsScreen(
                                userId        = userId,
                                viewModel     = repVM,
                                navController = navController
                            )
                        }

                        // Añadir repuesto (SIN token en ruta)
                        composable(
                            route = "addRepuesto/{userId}",
                            arguments = listOf(
                                navArgument("userId") { type = NavType.LongType }
                            )
                        ) { backStack ->
                            val userId = backStack.arguments!!.getLong("userId")

                            AddRepuestoScreen(
                                userId          = userId,
                                viewModel       = repVM,
                                onRepuestoAdded = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
