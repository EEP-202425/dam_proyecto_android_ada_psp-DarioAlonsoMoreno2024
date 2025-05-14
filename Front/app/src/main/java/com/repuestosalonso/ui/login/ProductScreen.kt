package com.repuestosalonso.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
    val repuestos by viewModel.repuestos.collectAsState(emptyList())
    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Estado para control de diálogo de edición
    var editing by remember { mutableStateOf<Repuesto?>(null) }
    var nombre by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var yearText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repuestos") },
                actions = {
                    IconButton(onClick = { viewModel.loadRepuestos(token) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addRepuesto/$token/$userId")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHost) }
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(repuestos) { rep ->
                Card(Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text("Modelo: ${rep.model}", style = MaterialTheme.typography.titleMedium)
                            Text("Precio: ${rep.precio}", style = MaterialTheme.typography.bodyMedium)
                            Text("Año: ${rep.year}", style = MaterialTheme.typography.bodySmall)
                        }
                        IconButton(onClick = {
                            // Abrir diálogo con valores iniciales
                            editing = rep
                            nombre = rep.model
                            precioText = rep.precio.toString()
                            yearText = rep.year.toString()
                        }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                        IconButton(onClick = {
                            scope.launch {
                                viewModel.deleteProduct(token, rep.id)
                                snackbarHost.showSnackbar("Repuesto eliminado")
                            }
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar")
                        }
                    }
                }
            }
        }

        // Diálogo de edición
        editing?.let { repuesto ->
            AlertDialog(
                onDismissRequest = { editing = null },
                title = { Text("Editar Repuesto") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Modelo") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = precioText,
                            onValueChange = { precioText = it },
                            label = { Text("Precio") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = yearText,
                            onValueChange = { yearText = it },
                            label = { Text("Año") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        val precio = precioText.toDoubleOrNull()
                        val year   = yearText.toIntOrNull()
                        if (nombre.isNotBlank() && precio != null && year != null) {
                            scope.launch {
                                // Aquí sí usamos updateRepuesto
                                viewModel.updateRepuesto(
                                    token, userId, repuesto.id,
                                    nombre, precio, year
                                )
                                snackbarHost.showSnackbar("Repuesto actualizado")
                                editing = null
                            }
                        } else {
                            scope.launch {
                                snackbarHost.showSnackbar("Rellena todos los campos")
                            }
                        }
                    }) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { editing = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}