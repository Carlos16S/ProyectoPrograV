package com.notes.notes.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.admin.v1.Index
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State



@HiltViewModel
class RegistroVieModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _email= MutableLiveData<String>()
    val email: LiveData<String> =_email
    private val _password= MutableLiveData<String>()
    val password: LiveData<String> =_password

    private val _nombre= MutableLiveData<String>()
    val nombre: LiveData<String> =_nombre

    private val _edad= MutableLiveData<String>()
    val edad: LiveData<String> =_edad
    private val _registroExitoso = mutableStateOf<Boolean?>(null)
    val registroExitoso: State<Boolean?> = _registroExitoso


fun resetearRegistro(){
    _registroExitoso.value=null
}
fun registrarUsuario(nombreU:String, correo:String,edad:String, password:String ) {
        _email.value=correo
         _nombre.value=nombreU
           _edad.value=edad
           _password.value=password
           var mensaje:String


        if (!nombreU.isNullOrEmpty() && !correo.isNullOrEmpty() &&
            !edad.isNullOrEmpty() && !password.isNullOrEmpty()) {

            auth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    val userId = auth.currentUser?.uid


                    val usuario = hashMapOf(

                        "nombre" to nombreU,
                        "correo" to correo,
                        "edad" to edad,
                        "contrasena" to password
                    )

                    userId?.let {
                        firestore.collection("Usuarios").document(it)
                            .set(usuario)
                            .addOnCompleteListener{task->
                                _registroExitoso.value=task.isSuccessful
                            }


                    }
                } else {
                    mensaje = "Error: ${task.exception?.message}"
                }

            }
        }else{

            mensaje = "Todos los campos son obligatorios"
        }


    }
    fun onLoginChanged(nombreU:String,password:String,edad:String,email:String){

        _nombre.value  =nombreU
        _password.value=password
        _edad.value=edad
        _email.value=email


    }



}

