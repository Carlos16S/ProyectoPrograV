package com.Notes.proyecto_carlos_maureen.myapplication.model

data class Usuarios( var id:  Int =0,
var nombre: String = "",
    val correo:String="",
    val edad:Int=0) {
    constructor():this(0,"","",0)}
