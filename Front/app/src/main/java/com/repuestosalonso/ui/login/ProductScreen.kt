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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.repuestosalonso.model.Repuesto
import com.repuestosalonso.network.TokenManager
import com.repuestosalonso.viewmodel.RepuestosViewModel
import kotlinx.coroutines.launch

@Composable
fun ProductsScreen(
    userId: Long,
    viewModel: RepuestosViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val token = TokenManager.getToken(context) ?: ""

    val repuestos by viewModel.repuestos.collectAsState(emptyList())
    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Cargar al entrar (solo 1 vez)
    LaunchedEffect(Unit) {
        if (token.isNotBlank()) {
            viewModel.loadRepuestos(token)
        } else {
            snackbarHost.showSnackbar("No hay token guardado. Vuelve a login.")
        }
    }

    var editing by remember { mutableStateOf<Repuesto?>(null) }
    var nombre by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var yearText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repuestos") },
                actions = {
                    IconButton(onClick = {
                        if (token.isNotBlank()) viewModel.loadRepuestos(token)
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refrescar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addRepuesto/$userId") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
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
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }

                            IconButton(onClick = {
                                scope.launch {
                                    if (token.isBlank()) {
                                        snackbarHost.showSnackbar("No hay token guardado.")
                                    } else {
                                        viewModel.deleteProduct(token, rep.id)
                                        snackbarHost.showSnackbar("Repuesto eliminado")
                                    }
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Borrar")
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    if (token.isBlank()) {
                                        snackbarHost.showSnackbar("No hay token guardado.")
                                        return@launch
                                    }
                                    val resp = viewModel.createOrder(token, userId, rep.id, 1)
                                    val msg = if (resp.isSuccessful) "Pedido enviado" else "Error al pedir (${resp.code()})"
                                    snackbarHost.showSnackbar(msg)
                                }
                            },
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
                                if (token.isBlank()) {
                                    snackbarHost.showSnackbar("No hay token guardado.")
                                    return@launch
                                }
                                viewModel.updateRepuesto(
                                    token, userId, repuesto.id,
                                    nombre, precio, year
                                )
                                snackbarHost.showSnackbar("Repuesto actualizado")
                                editing = null
                            }
                        } else {
                            scope.launch { snackbarHost.showSnackbar("Rellena todos los campos") }
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
