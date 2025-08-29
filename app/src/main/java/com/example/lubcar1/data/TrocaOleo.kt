package com.example.lubcar1.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "troca_oleo")
data class TrocaOleo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val idCliente: Long,
    val idVeiculo: Long,
    val data: String,
    val quilometragem: Int,
    val tipoOleo: String,
    val observacoes: String?
)

data class TrocaOleoComInfo(
    val troca: TrocaOleo,
    val clienteNome: String,
    val clienteSobrenome: String,
    val veiculoModelo: String,
    val veiculoPlaca: String
)