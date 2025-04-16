package com.notes.notes.model

data class NotasFB( var id: String = "",
                    var Titulo: String = "",
                    var Contenido: String = "",
                    var ContenidoMultimedia: String="",
                    var recordatorio:Boolean=false,
                    var usuarioId: String = "")
{
    constructor():this("","","","",false,"")


}
