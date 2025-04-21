package com.notes.notes.data.databases.Firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.notes.notes.model.NotasFB
import com.notes.notes.model.Recordatorios
import javax.inject.Inject


class Recordatorios_Firebase @Inject constructor(private val auth: FirebaseAuth,
                                                 private val firestore: FirebaseFirestore
) {
    private val coleccion1="Usuarios"
    private val collection2="Recordatorio"

    private val Usuario:String
        get() = auth.currentUser?.email?: throw IllegalStateException("Usuario no autenticado")

    fun obtenerRecordatorios(uid: String, onResult: (List<Recordatorios>) -> Unit) {
        firestore
            .collection(coleccion1)
            .document(uid) // Usar el `uid` que pasas como parámetro
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
                    onResult(emptyList()) // Si no hay datos, devolvemos lista vacía
                }
            }
    }


    fun elimiarRecordatorio(recordatorios: Recordatorios){
        if(recordatorios.id.isNotEmpty()){
            firestore.collection(coleccion1)
                .document(Usuario)
                .collection(collection2)
                .document(recordatorios.id)
                .delete()
                .addOnSuccessListener {
                    Log.d("DeleteObjeto", "Objeto eliminado")
                }
                .addOnCanceledListener {
                    Log.e("DeleteObjeto", "Objeto NO eliminado")

                }

        }




    }



}