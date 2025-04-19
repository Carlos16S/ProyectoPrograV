package com.notes.notes.viewModel

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import androidx.compose.runtime.State
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {

    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user

    private val _currentUser = mutableStateOf<FirebaseUser?>(null)
    val currentUser: State<FirebaseUser?> = _currentUser
    init {
        _user.value = auth.currentUser  // Verificar si el usuario ya está autenticado
    }



    private val _email= MutableLiveData<String>()
    val email: LiveData<String> =_email
    private val _password= MutableLiveData<String>()
    val password: LiveData<String> =_password

    private val _LoginEnable= MutableLiveData<Boolean>()
    val LoginEnable: LiveData<Boolean> =_LoginEnable

    private val _isLoading= MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> =_isLoading

    private  val _verificarUser= MutableLiveData<Boolean>()
    val verificarUser: LiveData<Boolean> =_verificarUser
    private val _idU= MutableLiveData<String>()
    val idU: LiveData<String> =_idU

    fun onLoginChanged(email:String,password:String){

        _email.value  =email
        _password.value=password
        _LoginEnable.value= EmailValidos(email)== true && Contrasenavalida(password) == true

    }

    private fun EmailValidos(email: String): Boolean? = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun Contrasenavalida(password: String):Boolean?= password.length >= 6

    private fun emailComprobante(){}

    fun login(email: String, password: String) {


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _verificarUser.value = task.isSuccessful
            }
            .addOnFailureListener {
                _verificarUser.value = false
            }
    }


    fun logout() {
        auth.signOut() // Cierra sesión en Firebase
        _user.value = null // Actualiza LiveData
        _currentUser.value = null // Actualiza State
    }


    suspend fun login_(email: String, password: String): Boolean {
        return suspendCancellableCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (continuation.isActive) {
                        continuation.resume(task.isSuccessful)
                    }
                }
                .addOnFailureListener {
                    if (continuation.isActive) {
                        continuation.resume(false)
                    }
                }
        }
    }


}
  // fun validarUsuario(email:String,password: String){

  //     val coleccion1="Usuarios"
  //     firestore.collection(coleccion1)
  //         .whereEqualTo("correo",email)

  //         .get()
  //         .addOnSuccessListener { querySnapshot ->
  //             _verificarUser.value = !querySnapshot.isEmpty // Si el documento existe, es true
  //         }
  //         .addOnFailureListener {
  //             _verificarUser.value = false // Error o usuario no encontrado
  //         }


  // }



