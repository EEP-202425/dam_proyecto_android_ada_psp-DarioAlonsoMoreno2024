package com.repuestosalonso.ui.login

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.repuestosalonso.network.TokenManager
import com.repuestosalonso.viewmodel.RepuestosViewModel
import kotlinx.coroutines.launch

@Composable
fun AddRepuestoScreen(
    userId: Long,
    viewModel: RepuestosViewModel,
    onRepuestoAdded: () -> Unit
) {
    val context = LocalContext.current
    val token = TokenManager.getToken(context) ?: ""

    val dispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    var nombre by remember { mutableStateOf("") }
    var precioText by remember { mutableStateOf("") }
    var yearText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Repuesto") },
                navigationIcon = {
                    IconButton(onClick = { dispatcher?.onBackPressed() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

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

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    val precio = precioText.toDoubleOrNull()
                    val year = yearText.toIntOrNull()

                    if (precio != null && year != null && nombre.isNotBlank()) {
                        scope.launch {
                            if (token.isBlank()) {
                                snackbarHostState.showSnackbar("No hay token guardado. Vuelve a login.")
                                return@launch
                            }

                            val resp = viewModel.createRepuesto(token, userId, nombre, precio, year)
                            if (resp.isSuccessful) {
                                // refrescar listado al volver
                                viewModel.loadRepuestos(token)
                                onRepuestoAdded()
                            } else {
                                snackbarHostState.showSnackbar("Error al guardar: ${resp.code()}")
                            }
                        }
                    } else {
                        scope.launch { snackbarHostState.showSnackbar("Rellena todos los campos") }
                    }
                },
                enabled = nombre.isNotBlank()
                        && precioText.toDoubleOrNull() != null
                        && yearText.toIntOrNull() != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
