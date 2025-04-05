package com.Notes.proyecto_carlos_maureen.myapplication.userinterfaz.Login

import android.provider.ContactsContract.CommonDataKinds.Email
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.Notes.proyecto_carlos_maureen.myapplication.viewModel.LoginViewModel
import com.google.firebase.database.ChildEvent
import kotlinx.coroutines.launch
import kotlin.math.sin


@Composable
fun LoginPantalla(viewModel: LoginViewModel,navController: NavController){
    val user by viewModel.user.observeAsState()
    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate("HomeScreen") {
                popUpTo("LoginScreen") { inclusive = true }  // Evita volver al login
            }
        }
    }
    Box(Modifier
        .fillMaxSize()
        .padding(16.dp)){

        Login(Modifier.align(Alignment.Center),viewModel)





    }
}

@Composable
fun Login(modifier: Modifier,viewModel: LoginViewModel) {
    val email:String by viewModel.email.observeAsState(initial = "")
    val contrasena:String by viewModel.password.observeAsState(initial = "")
    val isLoading:Boolean by viewModel.isLoading.observeAsState(initial = false)
    val loginEnable:Boolean by viewModel.LoginEnable.observeAsState(initial = false)
  val coroutineScope= rememberCoroutineScope()
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
           BotoLogin(loginEnable) {
               coroutineScope.launch {  viewModel.SelectorBoton() }
               }

       }
   }
}

@Composable
fun BotoLogin(loginEnable:Boolean,SelectorBoton:()->Unit) {
    Button(
        onClick = {SelectorBoton()},
        modifier = Modifier.fillMaxWidth().height(40.dp),
        colors =ButtonDefaults.buttonColors(
         contentColor = Color.White,
            disabledContentColor =Color.Red


        ),
        enabled = loginEnable




    ) {
    Text(text = "Iniciar sesion")

    }
}

@Composable
fun Olvidocontrasena(modifier:Modifier) {
    Text(text = "Olvidaste la contrasena",
        modifier = modifier.clickable {  },
        fontWeight = FontWeight.Bold,
        fontSize =12.sp
    )
}

@Composable
fun IngresarContrasena(contrasena: String,onTextFieldChanged: (String)-> Unit) {
    TextField(value = contrasena, onValueChange = {onTextFieldChanged(it) },
        placeholder ={ Text(text = "Contrasena")},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password),
        singleLine = true, maxLines = 1,
        )
    
}


@Composable
fun IngresarEmail(email: String,onTextFieldChanged: (String)-> Unit) {
    TextField(value = email, onValueChange ={onTextFieldChanged(it)},
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email")},
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Email),
        singleLine = true, maxLines = 1,

    )

}


