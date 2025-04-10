package com.notes.notes.model


data class Recordatorios( val id:String="",
                          val Contenido:String="",
                          val fechaRecordatorios: Long? = System.currentTimeMillis())
{ constructor():this("","",null)}