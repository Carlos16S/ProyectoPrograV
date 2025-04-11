package com.notes.notes.InterfazUsuario

import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle

@Composable
fun HomePantalla(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Pantalla principal!",
            modifier = Modifier.align(Alignment.CenterHorizontally) // Centra horizontalmente en la `Column`
        )
    }

}