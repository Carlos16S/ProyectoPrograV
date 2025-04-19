package com.notes.notes.viewModel

import androidx.lifecycle.ViewModel
import com.notes.notes.data.databases.Firebase.Notas_Firebase
import com.notes.notes.model.NotasFB
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditarViewModel @Inject constructor(private  val firebaserepository:Notas_Firebase): ViewModel()  {


    fun actualizarNota(nota: NotasFB) {
        firebaserepository.editarNota(nota)
    }
}