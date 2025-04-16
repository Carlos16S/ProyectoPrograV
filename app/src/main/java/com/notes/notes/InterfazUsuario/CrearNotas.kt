package com.notes.notes.InterfazUsuario

import android.icu.text.CaseMap.Title
import android.net.Uri
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.auth.User
import com.notes.notes.model.NotasFB
import com.notes.notes.model.Usuarios
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
    val titulo = viewModel.titulo
    val  contenido =viewModel.contenido
    val contenidoMultimedia=viewModel.contenidoMultimedia
    val IsRecordatorio=viewModel.isRecordatorio
 val multimediastring=contenidoMultimedia.toString()

    val usuarioID=viewModel.userId.toString()
    val notaAgregar = NotasFB(


        Titulo = titulo,
        Contenido = contenido,
        ContenidoMultimedia =multimediastring ,
        recordatorio = IsRecordatorio,
        usuarioId =usuarioID

       // Titulo = titulo,
       // Contenido = contenido,
       // ContenidoMultimedia = contenidoMultimedia,
       // recordatorio = isRecordatorio,
       // usuarios = usuario
    )

    Column {
        TittuloNota(titulo =titulo,onTituloChanged = { viewModel.onTituloChanged(it) } )
        Spacer(modifier = Modifier.padding(5.dp))
        ContenidoNota(Contenido = contenido,onContenidoChanged={viewModel.onContenidoChanged(it)})
        Spacer(modifier = Modifier.padding(5.dp))
        ContenidoMultimedia( onUriSelected = { uri ->
            viewModel.onContenidoMultimediaSeleccionado(uri)
        })
        Spacer(modifier = Modifier.padding(5.dp))
        IsRecordatorio(viewModel = viewModel)

        GuardarNota(navController =navController, viewModel = viewModel,notaAgregar )






    }

}

@Composable
fun GuardarNota(navController: NavController,viewModel: CrearViewModel,notasFB: NotasFB) {
    Box() {
        Button(
            onClick = { viewModel.agregarNotas(notasFB) },
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Guardar nota",
                tint = Color.White // O el color que quieras para el ícono
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espacio entre ícono y texto (opcional)
            Text("Eliminar") // Este texto es opcional
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
fun IsRecordatorio(viewModel: CrearViewModel)  {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = "¿Agregar como recordatorio?")
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = viewModel.isRecordatorio,
            onCheckedChange = { seleccionado ->
                viewModel.cambiarRecordatorio(seleccionado)
            }
        )
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


