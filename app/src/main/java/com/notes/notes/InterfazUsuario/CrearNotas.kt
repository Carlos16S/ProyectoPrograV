package com.notes.notes.InterfazUsuario

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.net.Uri
import android.view.View
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.notes.notes.model.NotasFB
import com.notes.notes.model.Recordatorios
import com.notes.notes.viewModel.CrearViewModel


@Composable
fun PantallaCrearNotas(viewModel: CrearViewModel,navController: NavController){
    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
    ){

        CrearNotas(Modifier, viewModel =viewModel,navController )
    }


}

@Composable
fun CrearNotas(modifier: Modifier,viewModel: CrearViewModel,navController: NavController) {

    LaunchedEffect(Unit) {
        viewModel.limpiarCampos()
    }
    val titulo = viewModel.titulo
    val  contenido =viewModel.contenido
    val contenidoMultimedia=viewModel.contenidoMultimedia
    val _Recordatorio=viewModel.is_Recordatorio
    val multimediastring=contenidoMultimedia.toString()

    val usuarioID=viewModel.userId.toString()
    val notaAgregar = NotasFB(


        Titulo = titulo,
        Contenido = contenido,
        ContenidoMultimedia =multimediastring ,
        recordatorio = _Recordatorio,
        usuarioId =usuarioID

    )
    val redordatorioAgregar = Recordatorios(titulo =titulo )

    Column {
        TittuloNota(
            titulo = titulo,
            onTituloChanged = { viewModel.onTituloChanged(it) }
        )


        if (!_Recordatorio) {
            Spacer(modifier = Modifier.padding(5.dp))

            ContenidoNota(
                Contenido = contenido,
                onContenidoChanged = { viewModel.onContenidoChanged(it) }
            )

            Spacer(modifier = Modifier.padding(5.dp))

         //  ContenidoMultimedia(onUriSelected = { uri ->
         //      viewModel.onContenidoMultimediaSeleccionado(uri)
         //  })

            Spacer(modifier = Modifier.padding(5.dp))
        }


        SelectorRecordatorio(viewModel = viewModel)


        GuardarNota(
            navController = navController,
            viewModel = viewModel,
            notaAgregar
        )
    }


}



@Composable
fun GuardarNota(navController: NavController,viewModel: CrearViewModel,notasFB: NotasFB) {
    Box() {
        Button(
            onClick = { viewModel.agregarNotas(notasFB)
                      navController.navigate("HomePantalla")},
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Guardar ",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Guardar")
        }
    }


}

@Composable
fun TittuloNota(titulo:String,onTituloChanged: (String)-> Unit) {
    TextField(value = titulo, onValueChange ={onTituloChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Titulo") },
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Text),
        singleLine = true, maxLines = 1,

        )


}

@Composable
fun SelectorRecordatorio(viewModel: CrearViewModel) {
    val calendar = remember { Calendar.getInstance() }
    val context = LocalContext.current

    // Estados para controlar cuándo mostrar los diálogos
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    // UI del switch
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "¿Agregar como recordatorio?")
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = viewModel.is_Recordatorio,
            onCheckedChange = { seleccionado ->
                viewModel.cambiarRecordatorio(seleccionado)
                if (seleccionado) {
                    showDatePicker = true
                }
            }
        )
    }

    // Mostrar DatePicker solo una vez cuando showDatePicker cambia a true
    LaunchedEffect(showDatePicker) {
        if (showDatePicker) {
            DatePickerDialog(
                context,
                { _, year, month, day ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    showDatePicker = false
                    showTimePicker = true
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }


    LaunchedEffect(showTimePicker) {
        if (showTimePicker) {
            TimePickerDialog(
                context,
                { _, hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    viewModel.setFechaHora(calendar)
                    showTimePicker = false
                    viewModel.programarNotificacion(context, titulo = viewModel.titulo)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }


    if (mostrarDialogo) {
        Dialog(onDismissRequest = { mostrarDialogo = false }) {
            ElegirFechaHora(
                onFechaHoraSeleccionada = { calendar ->
                    viewModel.setFechaHora(calendar)
                    viewModel.programarNotificacion(context, titulo = viewModel.titulo)
                    mostrarDialogo = false
                }
            )
        }
    }
}
@Composable
fun ContenidoMultimedia(onUriSelected: (Uri?) -> Unit) {
    val context = LocalContext.current


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        onUriSelected(uri) // Devolvés la URI seleccionada al ViewModel o estado
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Agregar imagen o video", fontWeight =  FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            launcher.launch("image/*") // También podés usar "video/*" o "*/*"
        }) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Seleccionar archivo")
        }
    }
}

@Composable
fun ContenidoNota(Contenido:String,onContenidoChanged: (String)-> Unit) {TextField(
    value = Contenido,
    onValueChange = { onContenidoChanged(it) },
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.5f)
        .padding(8.dp),
    placeholder = { Text(text = "Escribe tu nota aquí...") },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    maxLines = Int.MAX_VALUE,
    singleLine = false
)
}

@Composable
fun ElegirFechaHora(onFechaHoraSeleccionada: (Calendar) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var fechaElegida by remember { mutableStateOf<Calendar?>(null) }

    AndroidView(factory = { ctx ->
        DatePickerDialog(ctx, { _, year, month, dayOfMonth ->
            val fecha = Calendar.getInstance()
            fecha.set(Calendar.YEAR, year)
            fecha.set(Calendar.MONTH, month)
            fecha.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            TimePickerDialog(ctx, { _, hour, minute ->
                fecha.set(Calendar.HOUR_OF_DAY, hour)
                fecha.set(Calendar.MINUTE, minute)
                onFechaHoraSeleccionada(fecha)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).apply {
            show()
        }
        View(context)
    })
}


