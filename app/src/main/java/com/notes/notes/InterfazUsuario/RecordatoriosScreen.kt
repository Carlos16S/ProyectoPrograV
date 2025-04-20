package com.notes.notes.InterfazUsuario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RecordatorioPantalla(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título alineado arriba y centrado
        Titulo(modifier = Modifier.align(Alignment.TopCenter))

        // Contenido centrado vertical y horizontalmente
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MostrarRecordatorios()
            BotonSalir()
        }
    }
}

@Composable
fun BotonSalir() {

}

@Composable
fun MostrarRecordatorios() {

}

@Composable
fun Titulo(modifier: Modifier = Modifier) {
    Text(
        text = "Recordatorios",
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium
    )
}

