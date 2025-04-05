package com.Notes.proyecto_carlos_maureen.myapplication.userinterfaz.Login

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginPantalla(){
    Box(Modifier
        .fillMaxSize()
        .padding(16.dp)){
        Login(Modifier.align(Alignment.Center))





    }
}

@Composable
fun Login(modifier: Modifier) {
   Column(modifier=modifier) {
       //imagen(crear funcion)
       IngresarEmail()
       Spacer(modifier = Modifier.padding(4.dp))
       IngresarContrasena()
       Spacer(modifier = Modifier.padding(8.dp))
       Olvidocontrasena(Modifier.align(Alignment.End))
       Spacer(modifier = Modifier.padding(8.dp))
       BotoLogin()

   }
}

@Composable
fun BotoLogin() {
    Button(onClick = {},
        modifier = Modifier.fillMaxWidth().height(40.dp),
        colors =ButtonDefaults.buttonColors(
         contentColor = Color.White,
            disabledContentColor =Color.Red


        )




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
fun IngresarContrasena() {
    TextField(value = "", onValueChange = {  },
        placeholder ={ Text(text = "Contrasena")},
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Password),
        singleLine = true, maxLines = 1,
        )
    
}


@Composable
fun IngresarEmail() {
    TextField(value = "", onValueChange = {  },
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text(text = "Email")},
        keyboardOptions = KeyboardOptions(keyboardType= KeyboardType.Email),
        singleLine = true, maxLines = 1,

    )

}


