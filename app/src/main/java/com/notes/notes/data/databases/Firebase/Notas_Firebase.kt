package com.notes.notes.data.databases.Firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.notes.notes.model.NotasFB
import javax.inject.Inject

    class Notas_Firebase @Inject constructor(private val auth: FirebaseAuth,
                                             private val firestore: FirebaseFirestore) {

        private val coleccion1="AplicationBD"
        private val collection2="Notas"

        private val Usuario:String
            get() = auth.currentUser?.email?: throw IllegalStateException("Usuario no autenticado")





        fun guardarNotas(nota: NotasFB){
            val coleccionDestino = if(nota.recordatorio){ "Recordatorio"}else{"Notas"}


            val document = if (nota.id.isEmpty()) {
                // Nuevo documento con ID automático
                firestore.collection(coleccion1)
                    .document(Usuario)
                    .collection(coleccionDestino)
                    .document()
                    .also { nota.id = it.id }
            }else{
                // Si encuentra el id de la nota, la actualiza
                firestore
                    .collection(coleccion1)
                    .document(Usuario)
                    .collection(coleccionDestino)
                    .document(nota.id)


            }
            document.set(nota)
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Error al guardar documento", e)
                }


        }

        fun eliminarNotas(nota:NotasFB) {

            if (nota.id.isNotEmpty()) {
                firestore
                    .collection(coleccion1)
                    .document(Usuario)
                    .collection(collection2)
                    .document(nota.id)
                    .delete()
                    .addOnSuccessListener {
                        Log.d("DeleteObjeto", "Objeto eliminado")
                    }
                    .addOnCanceledListener {
                        Log.e("DeleteObjeto", "Objeto NO eliminado")

                    }

            }
        }
        fun getNotas(): MutableLiveData<List<NotasFB>> {
            val listaNotas = MutableLiveData<List<NotasFB>>()
            firestore
                .collection(coleccion1)
                .document(Usuario)
                .collection(collection2)
                .addSnapshotListener { instantanea, _error ->

                    if (_error != null) {
                        return@addSnapshotListener
                    }
                    if (instantanea != null) {

                        val lista = ArrayList<NotasFB>()
                        instantanea.documents.forEach {
                            var objetos = it.toObject(NotasFB::class.java)
                            if (objetos != null) {
                                lista.add(objetos)
                            }
                        }


                        listaNotas.value = lista
                    }
                }
            return listaNotas
        }
    }
