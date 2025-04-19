package com.notes.notes.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.notes.notes.data.databases.Firebase.Notas_Firebase
import com.notes.notes.model.NotasFB
import com.notes.notes.model.Usuarios
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseRepository: Notas_Firebase,
                                        private val auth: FirebaseAuth) : ViewModel()
{




    private val userId = auth.currentUser?.uid

    var notaSeleccionada:NotasFB? by mutableStateOf(null)
        private set

    var notas by mutableStateOf<List<NotasFB>>(emptyList())
        private set

    fun seleccionarNota(notas: NotasFB) {
        notaSeleccionada=notas

    }

    fun mostrarNotas() {
        userId?.let { uid ->
            firebaseRepository.obtenerNotasPorUsuario(uid) { nuevasNotas ->
                notas = nuevasNotas
            }
        } ?: run {
            notas = emptyList()
        }
    }

}