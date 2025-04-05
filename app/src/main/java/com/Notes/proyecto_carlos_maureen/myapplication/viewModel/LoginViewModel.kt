package com.Notes.proyecto_carlos_maureen.myapplication.viewModel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val auth:FirebaseAuth):ViewModel() {
    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> = _user
    init {
        _user.value = auth.currentUser  // Verificar si el usuario ya está autenticado
    }


    private val _email=MutableLiveData<String>()
            val email:LiveData<String> =_email
    private val _password=MutableLiveData<String>()
    val password:LiveData<String> =_password

    private val _LoginEnable=MutableLiveData<Boolean>()
    val LoginEnable:LiveData<Boolean> =_LoginEnable

    private val _isLoading=MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> =_isLoading

    fun onLoginChanged(email:String,password:String){

        _email.value  =email
        _password.value=password
        _LoginEnable.value= EmailValidos(email)== true && Contrasenavalida(password) == true

    }

    private fun EmailValidos(email: String): Boolean? = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun Contrasenavalida(password: String):Boolean?= password.length >= 6
    suspend fun SelectorBoton() {
        _isLoading.value=true
       delay(400)
        _isLoading.value=false
    }
    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser  // Actualiza el estado del usuario
                } else {
                    _user.value = null
                }
            }
    }


}
