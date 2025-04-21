package com.notes.notes.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.notes.notes.data.databases.Firebase.Recordatorios_Firebase
import com.notes.notes.model.NotasFB
import com.notes.notes.model.Recordatorios
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RecordatorioViewModel @Inject constructor(private val firebaseRepository: Recordatorios_Firebase,
                                                private val auth: FirebaseAuth
) : ViewModel() {




    private val userId: String?
        get() = auth.currentUser?.uid

    val usuarioID= userId


    var recordatorios by mutableStateOf<List<Recordatorios>>(emptyList())
        private set


    fun MostrarRecordatorios() {
        userId?.let { uid ->
            firebaseRepository.obtenerRecordatorios(uid) { nuevosrecordatorios ->
                Log.d("Firestore", "Recordatorios recibidos: $nuevosrecordatorios")
                recordatorios = nuevosrecordatorios
            }
        } ?: run {
            // Si no hay usuario logueado, limpias las notas
            recordatorios = emptyList()
        }
    }

    fun eliminarRecordatorio(){

        firebaseRepository.eliminarTodosLosRecordatorios()
    }
}