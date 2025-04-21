package com.notes.notes.InterfazUsuario

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.notes.notes.model.Recordatorios
import com.notes.notes.viewModel.RecordatorioViewModel

@Composable
fun RecordatorioPantalla(navController: NavController, viewModel: RecordatorioViewModel) {

    LaunchedEffect(Unit) {
        viewModel.MostrarRecordatorios()
    }
    val recordatorios=viewModel.recordatorios
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(20.dp))
        Titulo(modifier = Modifier.align(Alignment.TopCenter))
        Spacer(modifier = Modifier.padding(20.dp))
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.padding(20.dp))
            MostrarRecordatorios(viewModel = viewModel, recordatorios_ = recordatorios)
            BotonSalir()
        }
    }
}

@Composable
fun BotonSalir() {

}

@Composable
fun MostrarRecordatorios(viewModel: RecordatorioViewModel, recordatorios_: List<Recordatorios>) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (recordatorios_.isEmpty()) {
            Text(
                text = "No se encontraron recordatorios",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.TopCenter), // mostrar desde arriba
                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(8.dp) // espacio entre tarjetas
            ) {
                items(recordatorios_.size) { index ->
                    val recordatorio = recordatorios_[index]
                    val tituloSeguro = recordatorio.titulo ?: "Sin título"
                    val longitudTitulo = tituloSeguro.length

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3F51B5))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = tituloSeguro,
                                fontWeight = FontWeight.Bold,
                                fontSize = when {
                                    longitudTitulo <= 10 -> 24.sp
                                    longitudTitulo <= 20 -> 24.sp
                                    longitudTitulo <= 30 -> 18.sp
                                    else -> 14.sp
                                },
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}





@Composable
fun Titulo(modifier: Modifier = Modifier) {
    Text(
        text = "Recordatorios",
        modifier = modifier,
        style = MaterialTheme.typography.headlineMedium
    )
}

