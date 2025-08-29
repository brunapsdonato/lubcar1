package com.example.lubcar1.uistates
import com.example.lubcar1.data.Cliente
import com.example.lubcar1.data.Veiculo

data class EstadoTrocaOleo(
    val data: String = "",
    val quilometragem: String = "",
    val tipoOleo: String = "",
    val observacoes: String = "",
    val clienteSelecionado: Cliente? = null,
    val veiculoSelecionado: Veiculo? = null,
    val carregando: Boolean = false,
    val sucesso: Boolean = false,
    val erro: String? = null
)