package com.Notes.proyecto_carlos_maureen.myapplication.model

import java.sql.Date

data class Recordatorios( val id:String="",
        val Contenido:String="",
                          val fechaRecordatorios: Long? = System.currentTimeMillis())
{ constructor():this("","",null)}