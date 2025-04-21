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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.notes.notes.ui.theme.Terracota
import com.notes.notes.ui.theme.VerdeMusgo
import com.notes.notes.viewModel.RecordatorioViewModel

@Composable
fun RecordatorioPantalla(navController: NavController, viewModel: RecordatorioViewModel) {
    LaunchedEffect(Unit) {
        viewModel.MostrarRecordatorios()
    }
    val recordatorios = viewModel.recordatorios

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección superior con título
        Titulo(modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(20.dp))

        // Sección central con lista de recordatorios (scrollable)
        Box(
            modifier = Modifier
                .weight(1f) // Toma el espacio disponible, pero permite espacio para los botones abajo
        ) {
            MostrarRecordatorios(viewModel = viewModel, recordatorios_ = recordatorios)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección inferior con botones
        EliminarRecordatorios(viewModel = viewModel)

        Spacer(modifier = Modifier.height(8.dp))

        BotonSalir(navController = navController)
    }
}

@Composable
fun EliminarRecordatorios(viewModel: RecordatorioViewModel) {
    Button(
        onClick = {
            viewModel.eliminarRecordatorio() // Aquí llamas a la función que elimina todos
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        )
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = "Eliminar todos los recordatorios",
            color = Color.White
        )
    }
}

@Composable
fun BotonSalir(navController: NavController) {
    Button(
        onClick = {
            navController.navigate("HomePantalla") {
                // Evita duplicar pantallas en el backstack
                popUpTo("HomePantalla") { inclusive = true }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Terracota) // usa tu color personalizado
    ) {
        Text(text = "Salir", color = Color.White)
    }
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
                        colors = CardDefaults.cardColors(containerColor = VerdeMusgo)
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

