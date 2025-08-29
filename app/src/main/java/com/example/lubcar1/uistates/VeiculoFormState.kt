package com.example.lubcar1.uistates

import com.example.lubcar1.data.Cliente

data class VeiculoFormState(
    val id: Long? = null,
    val modelo: String = "",
    val placa: String = "",
    val ano: String = "",
    val tipoDeOleo: String = "",
    val clienteSelecionado: Cliente? = null,
    val listaClientes: List<Cliente> = emptyList(),
    val mensagem: String = ""
)