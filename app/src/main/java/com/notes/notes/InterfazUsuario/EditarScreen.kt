package com.notes.notes.InterfazUsuario

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.notes.notes.model.NotasFB
import com.notes.notes.viewModel.CrearViewModel
import com.notes.notes.viewModel.EditarViewModel
import com.notes.notes.viewModel.HomeViewModel

@Composable
fun PantallaEditarNota(
    viewModel: CrearViewModel,
    navController: NavController,
    notaExistente: NotasFB,
    viewModelE:EditarViewModel
) {


    // Inicializa los valores del ViewModel con la nota existente
    LaunchedEffect(Unit) {
        viewModel.onTituloChanged(notaExistente.Titulo)
        viewModel.onContenidoChanged(notaExistente.Contenido)
        viewModel.onContenidoMultimediaSeleccionado(Uri.parse(notaExistente.ContenidoMultimedia))
        viewModel.cambiarRecordatorio(notaExistente.recordatorio)

        // ✅ También inicializa el EditarViewModel con la nota seleccionada
        viewModelE.seleccionarNota(notaExistente)
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        EditarNotaContenido(viewModel = viewModel, navController = navController, notaOriginal = notaExistente,viewModelE=viewModelE)
    }
}

@Composable
fun EditarNotaContenido(
    viewModel: CrearViewModel,
    navController: NavController,
    notaOriginal: NotasFB,
    viewModelE:EditarViewModel

) {
    val titulo = viewModel.titulo
    val contenido = viewModel.contenido
    val contenidoMultimedia = viewModel.contenidoMultimedia
    val isRecordatorio = viewModel.is_Recordatorio
    val usuarioID = viewModel.userId.toString()

    val notaEditada = NotasFB(
        id = notaOriginal.id,
        Titulo = titulo,
        Contenido = contenido,
        ContenidoMultimedia = contenidoMultimedia.toString(),
        recordatorio = isRecordatorio,
        usuarioId = usuarioID
    )

    Column {
        TittuloNota(titulo = titulo, onTituloChanged = { viewModel.onTituloChanged(it) })
        Spacer(modifier = Modifier.padding(5.dp))
        ContenidoNota(Contenido = contenido, onContenidoChanged = { viewModel.onContenidoChanged(it) })
        Spacer(modifier = Modifier.padding(5.dp))
        ContenidoMultimedia(onUriSelected = { uri ->
            viewModel.onContenidoMultimediaSeleccionado(uri)
        })
        BotonGuardarCambios(navController = navController, viewModel = viewModel, notaEditada,viewModelE=viewModelE)
        BotonElminar(navController = navController,viewModel=viewModelE )






    }
}
@Composable
fun BotonElminar(navController: NavController, viewModel: EditarViewModel) {
    val nota = viewModel.notaSeleccionada

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = {
                nota?.let {
                    viewModel.eliminarNota(it)
                    navController.navigate("HomePantalla")
                }
            },
            modifier = Modifier.align(Alignment.BottomStart),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Eliminar nota", color = Color.White)
        }
    }
}



@Composable
fun BotonGuardarCambios(navController: NavController, viewModel: CrearViewModel, nota: NotasFB,viewModelE: EditarViewModel) {
    Box {
        Button(
            onClick = {
                viewModelE.actualizarNota(nota) // Deberías implementar esta función en el ViewModel
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Editar nota",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Guardar Cambios")
        }
    }
}
