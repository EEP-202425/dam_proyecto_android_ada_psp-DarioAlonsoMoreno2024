package com.repuestosalonso.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenido a Repuestos Alonso", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Navegar a otra pantalla en el futuro */ }) {
            Text("Ver Repuestos")
        }
    }
}
