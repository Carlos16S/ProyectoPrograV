package com.notes.notes.InterfazUsuario.Login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.notes.notes.viewModel.LoginViewModel
import kotlinx.coroutines.launch


@Composable
fun LoginPantalla(  viewModel: LoginViewModel , navController: NavController){


    Box(
        Modifier
        .fillMaxSize()
        .padding(16.dp)){

        Login(Modifier.align(Alignment.Center), viewModel =viewModel,navController )





    }
}

@Composable
fun Login(modifier: Modifier, viewModel: LoginViewModel,navController: NavController) {
    val email:String by viewModel.email.observeAsState(initial = "")
    val contrasena:String by viewModel.password.observeAsState(initial = "")
    val isLoading:Boolean by viewModel.isLoading.observeAsState(initial = false)
    val loginEnable:Boolean by viewModel.LoginEnable.observeAsState(initial = false)
    val coroutineScope= rememberCoroutineScope()
    val userExists: Boolean? by viewModel.verificarUser.observeAsState(initial = false)
    val idUsuario: String by viewModel.idU.observeAsState(initial = "")
    val context = LocalContext.current
    val currentUser by viewModel.currentUser

    if(isLoading){
        Box(Modifier.fillMaxSize()){
            CircularProgressIndicator(Modifier.align(Alignment.Center))

        }
    }
    else {
        Column(modifier = modifier) {
            //imagen(crear funcion)
            IngresarEmail(email) { viewModel.onLoginChanged(it, contrasena) }
            Spacer(modifier = Modifier.padding(4.dp))
            IngresarContrasena(contrasena) { viewModel.onLoginChanged(email, it) }
            Spacer(modifier = Modifier.padding(8.dp))
            Olvidocontrasena(Modifier.align(Alignment.End))
            Spacer(modifier = Modifier.padding(8.dp))
            BotoLogin(loginEnable, navController) {
                coroutineScope.launch {
                    val success = viewModel.login_(email, contrasena)
                    if (success) {
                        navController.navigate("HomePantalla")
                    } else {
                        Toast.makeText(context, "Usuario no válido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // Observa userExists y reacciona cuando cambia:

            Spacer(modifier = Modifier.padding(8.dp))
            BotonRegistro(
                navController
            )

        }
    }
}

@Composable
fun BotonRegistro(navController: NavController)
{
    Button(onClick ={navController.navigate("RegistroPantalla")},
        modifier = Modifier.fillMaxWidth().height(40.dp)
    )
    {
        Text(text = "Crear Usuario")


    }

}

@Composable
fun BotoLogin(loginEnable:Boolean,navController: NavController,     SelectorBoton:()->Unit) {
    Button(
        onClick = {SelectorBoton() },
        modifier = Modifier.fillMaxWidth().height(40.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            disabledContentColor = Color.Red


        ),
        enabled = loginEnable,





        ) {
        Text(text = "Iniciar sesion")

    }
}

@Composable
fun Olvidocontrasena(modifier: Modifier) {
    Text(text = "Olvidaste la contrasena",
        modifier = modifier.clickable {  },
        fontWeight = FontWeight.Bold,
        fontSize =12.sp
    )
}

@Composable
fun IngresarContrasena(contrasena: String,onTextFieldChanged: (String)-> Unit) {
    TextField(value = contrasena, onValueChange = {onTextFieldChanged(it) },
        placeholder ={ Text(text = "Contrasena") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password),
        singleLine = true, maxLines = 1,
    )

}


@Composable
fun IngresarEmail(email: String,onTextFieldChanged: (String)-> Unit) {
    TextField(value = email, onValueChange ={onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email") },
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Email),
        singleLine = true, maxLines = 1,

        )

}
