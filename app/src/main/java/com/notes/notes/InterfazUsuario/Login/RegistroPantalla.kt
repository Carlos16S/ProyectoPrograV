package com.notes.notes.InterfazUsuario.Login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

import com.notes.notes.viewModel.LoginViewModel
import com.notes.notes.viewModel.RegistroVieModel




@Composable
fun RegistroPantalla(viewModel:RegistroVieModel, navController: NavController){



    Box(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
              )
           {



        Resgistro( Modifier.align(Alignment.Center),viewModel=viewModel, navController =navController  )



    }

}

@Composable
fun Resgistro(modifier: Modifier, viewModel: RegistroVieModel, navController: NavController) {
    val nombreU:String by viewModel.nombre.observeAsState(initial = "")
    val contrasena:String by viewModel.password.observeAsState(initial = "")
    val correo:String by viewModel.email.observeAsState(initial = "")
    val edad:String by viewModel.edad.observeAsState(initial = "")

    //Imagen
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Top, // Asegura que los elementos se apilen desde arriba
        horizontalAlignment = Alignment.CenterHorizontally ) {


        Spacer(modifier.padding(4.dp))
        TextoNombre( nombreU ){viewModel.onLoginChanged(it , viewModel.password.value ?: "", viewModel.edad.value ?: "", viewModel.email.value ?: "")}
        Spacer(modifier.padding(4.dp))
        TextoCorreo(correo){viewModel.onLoginChanged(viewModel.nombre.value ?: "", viewModel.password.value?:"", viewModel.edad.value ?: "", it)}
        Spacer(modifier.padding(4.dp))
        TextoEDAD(edad){viewModel.onLoginChanged(viewModel.nombre.value ?: "", viewModel.password.value?:"", it, viewModel.email.value ?: "")}
        Spacer(modifier.padding(4.dp))
        TextoPassword(contrasena) {viewModel.onLoginChanged(viewModel.nombre.value ?: "", it,viewModel.edad.value ?: "", viewModel.email.value ?: "")}
        Spacer(modifier.padding(4.dp))
        BotonConfirmar(viewModel = viewModel, correo =correo, nombre = nombreU, edad =  edad, contrasena = contrasena)
    }





}

@Composable
fun BotonConfirmar(viewModel: RegistroVieModel,correo: String,nombre: String,edad: String,contrasena: String) {
    val context = LocalContext.current
    val registroExitoso by viewModel.registroExitoso
    LaunchedEffect(registroExitoso) {
        registroExitoso?.let { exito ->
            if (exito) {
                Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
            }
            viewModel.resetearRegistro()
        }
    }
    Button(
        onClick = {
            viewModel.registrarUsuario(nombreU = nombre, correo = correo, edad = edad, password = contrasena)
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContentColor = Color.Red
        )
    ) {
        Text(text = "Confirmar Registro")
    }
}





@Composable
fun TextoPassword(contrasena:String,onTextFieldChanged: (String)-> Unit ) {
    TextField(value = contrasena, onValueChange ={onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Contraseña") },
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Text),
        singleLine = true, maxLines = 1,

        )

}

@Composable
fun TextoEDAD(edad:String,onTextFieldChanged: (String)-> Unit) {
    TextField(value = edad, onValueChange ={onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Edad") },
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Number),
        singleLine = true, maxLines = 1,

        )
}

@Composable
fun TextoCorreo(correo:String,onTextFieldChanged: (String)-> Unit) {
    TextField(value = correo, onValueChange ={onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Email),
        singleLine = true, maxLines = 1,

        )
}

@Composable
fun TextoNombre(nombre:String,onTextFieldChanged: (String)-> Unit) {
    TextField(value = nombre, onValueChange ={onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Nombre") },
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Text),
        singleLine = true, maxLines = 1,

        )
}
