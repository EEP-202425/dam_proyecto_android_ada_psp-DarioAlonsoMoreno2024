package com.repuestosalonso.ui.login

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
    userId: Long,
    viewModel: RepuestosViewModel,
    navController: NavHostController
) {
    val repuestos by viewModel.repuestos.collectAsState(emptyList())
    val snackbarHost = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Cargar lista al entrar (1 vez)
    LaunchedEffect(Unit) {
        viewModel.loadRepuestos()
    }

    // Estado para diálogo de edición
    var editing by remember { mutableStateOf<Repuesto?>(null) }

    var nombre by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var yearText by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var stockText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repuestos") },
                actions = {
                    IconButton(onClick = { viewModel.loadRepuestos() }) {
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
            modifier = Modifier
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
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(Modifier.weight(1f)) {
                                Text(
                                    text = "Nombre: ${rep.nombre}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Marca: ${rep.marca}",
                                    style = MaterialTheme.typography.bodyMedium
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
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = "Stock: ${rep.stock}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }

                            IconButton(onClick = {
                                editing = rep
                                nombre = rep.nombre
                                precioText = rep.precio.toString()
                                yearText = rep.year.toString()
                                marca = rep.marca
                                stockText = rep.stock.toString()
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }

                            IconButton(onClick = {
                                scope.launch {
                                    viewModel.deleteProduct(rep.id)
                                    snackbarHost.showSnackbar("Repuesto eliminado")
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Borrar")
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        Button(
                            onClick = {
                                scope.launch {
                                    val resp = viewModel.createOrder(userId, rep.id, 1)
                                    val msg = if (resp.isSuccessful) {
                                        "Pedido enviado"
                                    } else {
                                        "Error al pedir (${resp.code()})"
                                    }
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

        // Diálogo de edición (IMPORTANTE: envía marca y stock)
        editing?.let { repuesto ->
            AlertDialog(
                onDismissRequest = { editing = null },
                title = { Text("Editar Repuesto") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = marca,
                            onValueChange = { marca = it },
                            label = { Text("Marca") },
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
                        OutlinedTextField(
                            value = stockText,
                            onValueChange = { stockText = it },
                            label = { Text("Stock") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(onClick = {
                        val precio = precioText.toDoubleOrNull()
                        val year = yearText.toIntOrNull()
                        val stock = stockText.toIntOrNull()

                        if (nombre.isNotBlank() && marca.isNotBlank() && precio != null && year != null && stock != null) {
                            scope.launch {
                                viewModel.updateRepuesto(
                                    userId = userId,
                                    productId = repuesto.id,
                                    nombre = nombre,
                                    precio = precio,
                                    year = year,
                                    marca = marca,
                                    stock = stock
                                )
                                snackbarHost.showSnackbar("Repuesto actualizado")
                                editing = null
                                // refresco por si backend devuelve algo distinto
                                viewModel.loadRepuestos()
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
