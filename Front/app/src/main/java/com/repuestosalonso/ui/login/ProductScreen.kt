package com.repuestosalonso.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.repuestosalonso.viewmodel.RepuestosViewModel
import com.repuestosalonso.viewmodel.UiListState

@Composable
fun ProductsScreen(
    token: String,
    viewModel: RepuestosViewModel,
    navController: NavController
) {
    // Observa el estado del ViewModel
    val state by viewModel.state.observeAsState(UiListState.Loading)
    val context = LocalContext.current

    // Carga los repuestos cuando aparezca la pantalla
    LaunchedEffect(token) {
        viewModel.loadRepuestos(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Repuestos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            when (state) {
                UiListState.Loading -> {
                    Box(
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UiListState.Error -> {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: ${(state as UiListState.Error).message}")
                    }
                }
                is UiListState.Success -> {
                    val list = (state as UiListState.Success).data
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(list) { repuesto ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Column(Modifier.padding(8.dp)) {
                                    Text(
                                        text = "Modelo: ${repuesto.model}",
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "Precio: ${repuesto.precio}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Año: ${repuesto.year}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Button(onClick = {
                                        // Acción de pedir
                                        Toast.makeText(
                                            context,
                                            "Pedido solicitado: ${repuesto.model}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }) {
                                        Text("Pedir")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
