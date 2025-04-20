package com.notes.notes.viewModel

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.notes.notes.AlarmReceiver
import com.notes.notes.data.databases.Firebase.Notas_Firebase
import com.notes.notes.model.NotasFB
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CrearViewModel @Inject constructor(private val firebaseRepository: Notas_Firebase,
                                         private val auth: FirebaseAuth
) : ViewModel() {


    var titulo by mutableStateOf("")
        private set

    var contenido by mutableStateOf("")
        private set

    var contenidoMultimedia by mutableStateOf<Uri?>(null)
        private set
    val userId = FirebaseAuth.getInstance().currentUser?.uid



fun agregarNotas(nota:NotasFB){


    firebaseRepository.guardarNotas(nota)

}


    fun onTituloChanged(nuevoTitulo: String) {
        titulo = nuevoTitulo
    }

    fun onContenidoChanged(nuevoContenido: String) {
        contenido = nuevoContenido
    }


    var is_Recordatorio by mutableStateOf(false)
        private set

    fun cambiarRecordatorio(nuevoValor: Boolean) {
        is_Recordatorio = nuevoValor
    }
    fun onContenidoMultimediaSeleccionado(uri: Uri?) {

        contenidoMultimedia = uri
    }

    var fechaHoraRecordatorio by mutableStateOf<Long?>(null)
        private set

    fun setFechaHora(calendar: android.icu.util.Calendar) {
        this.fechaHoraRecordatorio = calendar.timeInMillis
    }

    @SuppressLint("ScheduleExactAlarm")
    fun programarNotificacion(context: Context, titulo: String) {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("titulo", titulo)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        fechaHoraRecordatorio?.let {
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                it,
                pendingIntent
            )
        }
    }

    fun limpiarCampos() {
        titulo = ""
        contenido = ""
        contenidoMultimedia = null
        is_Recordatorio = false
    }






}