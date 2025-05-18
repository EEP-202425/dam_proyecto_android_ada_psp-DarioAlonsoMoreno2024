package com.repuestosalonso.ui.login

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
                        Icon(
                            Icons.Default.Refresh,
                            contentDescription = "Refrescar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addRepuesto/$token/$userId") },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir", tint = MaterialTheme.colorScheme.onPrimary)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHost) }
    ) { padding ->
        LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(repuestos) { rep ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = "Modelo: ${rep.model}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Precio: ${rep.precio}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Año: ${rep.year}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            IconButton(onClick = {
                                editing = rep
                                nombre = rep.model
                                precioText = rep.precio.toString()
                                yearText = rep.year.toString()
                            }) {
                                Icon(
                                    Icons.Default.Edit,
                                    contentDescription = "Editar",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = {
                                scope.launch {
                                    viewModel.deleteProduct(token, rep.id)
                                    snackbarHost.showSnackbar("Repuesto eliminado")
                                }
                            }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Borrar",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    val resp = viewModel.createOrder(token, userId, rep.id, 1)
                                    val msg = if (resp.isSuccessful) "Pedido enviado" else "Error al pedir"
                                    snackbarHost.showSnackbar(msg)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Pedir")
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
                        val year = yearText.toIntOrNull()
                        if (nombre.isNotBlank() && precio != null && year != null) {
                            scope.launch {
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
