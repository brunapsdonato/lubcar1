package com.example.lubcar1.model

import com.google.firebase.firestore.DocumentId

data class Veiculo(
    @DocumentId
    var id: String = "",
    var modelo: String = "",
    var placa: String = "",
    var ano: Int = 0,
    var tipoDeOleo: String = "",
    var clienteId: String = "",
    var clienteNome: String = ""

)