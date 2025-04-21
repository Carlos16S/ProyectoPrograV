package com.notes.notes.data.databases.Firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.notes.notes.InterfazUsuario.MostrarRecordatorios
import com.notes.notes.model.NotasFB
import com.notes.notes.model.Recordatorios
import javax.inject.Inject


class Recordatorios_Firebase @Inject constructor(private val auth: FirebaseAuth,
                                                 private val firestore: FirebaseFirestore
) {
    private val coleccion1="Usuarios"
    private val collection2="Recordatorio"

    private val Usuario:String
        get() = auth.currentUser?.uid?: throw IllegalStateException("Usuario no autenticado")



    fun obtenerRecordatorios(uid: String, onResult: (List<Recordatorios>) -> Unit) {
        firestore
            .collection(coleccion1)
            .document(uid)
            .collection(collection2)
            .addSnapshotListener { snapshot, error ->

                if (error != null) {
                    Log.e("Firestore", "Error al obtener recordatorios", error)
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    val lista = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Recordatorios::class.java)
                    }
                    onResult(lista)
                } else {
                    onResult(emptyList())
                }
            }
    }


    fun eliminarTodosLosRecordatorios() {

        val referencia = firestore.
            collection(coleccion1)
            .document(Usuario)
            .collection(collection2)


        referencia.get()
            .addOnSuccessListener { snapshot ->

                val batch = firestore.batch()


                for (documento in snapshot.documents) {
                    batch.delete(documento.reference)
                }


                batch.commit()
                    .addOnSuccessListener {
                        Log.d("Firestore", "Todos los recordatorios eliminados correctamente")

                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error al eliminar recordatorios: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error al obtener recordatorios: ${e.message}")
            }
    }




}