package com.notes.notes.data.databases.Firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.notes.notes.model.NotasFB
import com.notes.notes.model.Usuarios
import javax.inject.Inject

    class Notas_Firebase @Inject constructor(private val auth: FirebaseAuth,
                                             private val firestore: FirebaseFirestore) {


        private val collection1="Notas"

        private val Usuario:String
            get() = auth.currentUser?.uid?: throw IllegalStateException("Usuario no autenticado")





        fun guardarNotas(nota: NotasFB){
            val coleccionDestino = if(nota.recordatorio){ "Recordatorio"}else{"Notas"}


            val document = if (nota.id.isEmpty()) {
                // Nuevo documento con ID automático
                firestore.collection("Usuarios")
                    .document(Usuario)
                    .collection(coleccionDestino)
                    .document().also {
                        nota.id = it.id             // Asigna ID automáticamente
                    }
            }else{
                // Si encuentra el id de la nota, la actualiza
                firestore
                    .collection("Usuarios")
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
                    .collection(collection1)
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
        fun obtenerNotasPorUsuario(uid: String, onResult: (List<NotasFB>) -> Unit) {
            val firestore = FirebaseFirestore.getInstance()


            firestore.collection("Usuarios")
                .document(uid)
                .collection("Notas")
                .addSnapshotListener { notasSnapshot, error ->
                    if (error != null) {

                        onResult(emptyList())
                        return@addSnapshotListener
                    }

                    // Convertir los documentos a objetos NotasFB
                    val notas = notasSnapshot?.toObjects(NotasFB::class.java) ?: emptyList()
                    onResult(notas)
                }
        }

    }
