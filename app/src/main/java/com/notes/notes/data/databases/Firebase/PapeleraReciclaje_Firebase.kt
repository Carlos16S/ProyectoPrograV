package com.notes.notes.data.databases.Firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.notes.notes.model.papelera_Reciclaje
import javax.inject.Inject

class PapeleraReciclaje_Firebase {
    class PapeleraReciclaje_Firebase @Inject constructor(private val auth: FirebaseAuth,
                                                         private val firestore: FirebaseFirestore
    ){


        private val coleccion1="AplicationBD"
        private val coleccion2="papeleraReciclaje"


        private val Usuario:String
            get()=auth.currentUser?.email?:throw IllegalStateException("Usuario no autenticado")

        fun obtenerPapelera(): MutableLiveData<List<papelera_Reciclaje>> {
            val listaPapelera= MutableLiveData<List<papelera_Reciclaje>>()
            firestore.collection(coleccion1)
                .document(Usuario)
                .collection(coleccion2)
                .addSnapshotListener { instantanea, _error ->

                    if (_error != null) {
                        return@addSnapshotListener
                    }
                    if (instantanea != null) {

                        val lista = ArrayList<papelera_Reciclaje>()
                        instantanea.documents.forEach {
                            var objetos = it.toObject(papelera_Reciclaje::class.java)
                            if (objetos != null) {
                                lista.add(objetos)
                            }
                        }


                        listaPapelera.value = lista
                    }
                }

            return listaPapelera

        }
        fun agregarPapelera(papelera:papelera_Reciclaje){






        }
}
}