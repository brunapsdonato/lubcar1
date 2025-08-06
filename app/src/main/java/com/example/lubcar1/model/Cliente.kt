package com.example.lubcar1.model

import com.google.firebase.firestore.DocumentId

data class Cliente(
    @DocumentId
    var id: String = "",
    var nome: String = "",
    var sobrenome: String = "",
    var telefone: String = "",
    var email: String = "",
    var cpf: String = ""
)