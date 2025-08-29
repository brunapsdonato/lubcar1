package com.example.lubcar1.uistates

import com.example.lubcar1.data.Cliente
import com.example.lubcar1.data.Veiculo
import java.time.LocalDate

data class TrocaOleoFormState(
    val id: Long? = null,
    val veiculoSelecionado: Veiculo? = null,
    val clienteRelacionado: Cliente? = null,
    val tipoDeOleo: String = "",
    val quilometragemAtual: String = "",
    val dataTroca: LocalDate? = null,
    val observacoes: String = "",
    val listaVeiculos: List<Veiculo> = emptyList(),
    val mensagem: String = ""
)