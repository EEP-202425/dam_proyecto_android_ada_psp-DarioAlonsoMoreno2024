package com.repuestosalonso.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.repuestosalonso.model.Repuesto
import com.repuestosalonso.viewmodel.RepuestosViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductsScreen(
    token: String,
    userId: Long,
    viewModel: RepuestosViewModel,
    navController: NavHostController
) {
    val repuestos by viewModel.repuestos.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Repuestos") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addRepuesto/$token/$userId")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(repuestos) { repuesto ->
                Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Modelo: ${repuesto.model}", style = MaterialTheme.typography.titleMedium)
                            Text("Precio: ${repuesto.precio}", style = MaterialTheme.typography.bodyMedium)
                            Text("Año: ${repuesto.year}", style = MaterialTheme.typography.bodySmall)
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    scope.launch {
                                        val resp = viewModel.createOrder(token, userId, repuesto.id, 1)
                                        val msg = if (resp.isSuccessful) "Pedido enviado" else "Error al pedir"
                                        snackbarHostState.showSnackbar(msg)
                                    }
                                },
                                Modifier.fillMaxWidth()
                            ) {
                                Text("Pedir")
                            }
                        }
                        IconButton(onClick = {
                            scope.launch {
                                viewModel.deleteProduct(token, repuesto.id)
                                snackbarHostState.showSnackbar("Repuesto eliminado")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

