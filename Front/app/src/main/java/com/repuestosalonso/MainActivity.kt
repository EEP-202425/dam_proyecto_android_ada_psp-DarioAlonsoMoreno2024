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
                    val navController: NavHostController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(
                                viewModel = userVM,
                                onLoginSuccess = { token, userId ->
                                    navController.navigate("products/$token/$userId") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }

                        // Pantalla de listado de productos
                        composable(
                            route = "products/{token}/{userId}",
                            arguments = listOf(
                                navArgument("token")  { type = NavType.StringType },
                                navArgument("userId") { type = NavType.LongType }
                            )
                        ) { backStack ->
                            val token  = backStack.arguments!!.getString("token")!!
                            val userId = backStack.arguments!!.getLong("userId")
                            repVM.loadRepuestos(token)
                            ProductsScreen(
                                token         = token,
                                userId        = userId,
                                viewModel     = repVM,
                                navController = navController
                            )
                        }

                        // Pantalla de añadir repuesto
                        composable(
                            route = "addRepuesto/{token}/{userId}",
                            arguments = listOf(
                                navArgument("token")  { type = NavType.StringType },
                                navArgument("userId") { type = NavType.LongType }
                            )
                        ) { backStack ->
                            val token  = backStack.arguments!!.getString("token")!!
                            val userId = backStack.arguments!!.getLong("userId")
                            AddRepuestoScreen(
                                token           = token,
                                userId          = userId,
                                viewModel       = repVM,
                                onRepuestoAdded = {
                                    repVM.loadRepuestos(token)
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
