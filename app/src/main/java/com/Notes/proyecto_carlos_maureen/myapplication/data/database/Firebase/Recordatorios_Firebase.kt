package com.Notes.proyecto_carlos_maureen.myapplication.data.database.Firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.Notes.proyecto_carlos_maureen.myapplication.model.Recordatorios
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class Recordatorios_Firebase @Inject constructor(private val auth: FirebaseAuth,
                                                 private val firestore: FirebaseFirestore
) {
    private val coleccion1="AplicationBD"
    private val collection2="Recordatorio"

    private val Usuario:String
        get() = auth.currentUser?.email?: throw IllegalStateException("Usuario no autenticado")

    fun obtenerRecordatorios(): MutableLiveData<List<Recordatorios>> {
        val listaRecordatorios = MutableLiveData<List<Recordatorios>>()
        firestore
            .collection(coleccion1)
            .document(Usuario)
            .collection(collection2)
            .addSnapshotListener { instantanea, _error ->

                if (_error != null) {
                    return@addSnapshotListener
                }
                if (instantanea != null) {

                    val lista = ArrayList<Recordatorios>()
                    instantanea.documents.forEach {
                        var objetos = it.toObject(Recordatorios::class.java)
                        if (objetos != null) {
                            lista.add(objetos)
                        }
                    }


                    listaRecordatorios.value = lista
                }
            }
        return listaRecordatorios



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
