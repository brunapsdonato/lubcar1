package com.example.lubcar1.data

import androidx.room.PrimaryKey
import androidx.room.Entity

@Entity(tableName = "veiculo")
data class Veiculo(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val placa: String,
    val modelo: String,
    val ano: Int,
    val clienteId: Long,
    val tipoDeOleo: String,
    val clienteNome: String
)