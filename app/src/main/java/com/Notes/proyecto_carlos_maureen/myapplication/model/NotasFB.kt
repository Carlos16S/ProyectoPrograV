package com.Notes.proyecto_carlos_maureen.myapplication.model

data class NotasFB( var id: String = "",
                    var Titulo: String = "",
                    var Contenido: String = "",
                    var ContenidoMultimedia: String="",
                    var recordatorio:Boolean=false)
{
    constructor():this("","","","",false)


}
