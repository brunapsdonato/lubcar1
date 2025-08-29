package com.example.lubcar1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cliente")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var nome: String = "",
    var sobrenome: String = "",
    var telefone: String = "",
    var email: String = "",
    var cpf: String = ""
)