package com.notes.notes.model

data class Configuraciones(
    val id:Int=0,
    val TemaAplicacion:Boolean= false,
    val colorLetra:String="",
    val colorBotones:String=""
){
    constructor():this(0,false,"","")
}
