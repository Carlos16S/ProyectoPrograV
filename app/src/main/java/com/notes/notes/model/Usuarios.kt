package com.notes.notes.model

data class Usuarios( val id:  Int =0,
                     val nombre: String = "",
                     val correo:String="",
                     val edad:Int=0,
                     val password:String="") {
    constructor():this(0,"","",0,"")}

