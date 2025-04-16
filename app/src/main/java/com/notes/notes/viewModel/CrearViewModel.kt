package com.notes.notes.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.notes.notes.data.databases.Firebase.Notas_Firebase
import com.notes.notes.model.NotasFB
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CrearViewModel @Inject constructor(private val firebaseRepository: Notas_Firebase,
                                         private val auth: FirebaseAuth
) : ViewModel() {


    var titulo by mutableStateOf("")
        private set

    var contenido by mutableStateOf("")
        private set

    var contenidoMultimedia by mutableStateOf<Uri?>(null)
        private set
    val userId = FirebaseAuth.getInstance().currentUser?.uid



fun agregarNotas(nota:NotasFB){


    firebaseRepository.guardarNotas(nota)

}

    // Funciones para actualizar los campos
    fun onTituloChanged(nuevoTitulo: String) {
        titulo = nuevoTitulo
    }

    fun onContenidoChanged(nuevoContenido: String) {
        contenido = nuevoContenido
    }


    var isRecordatorio by mutableStateOf(false)
        private set

    fun cambiarRecordatorio(nuevoValor: Boolean) {
        isRecordatorio = nuevoValor
    }
    fun onContenidoMultimediaSeleccionado(uri: Uri?) {

        contenidoMultimedia = uri
    }





}