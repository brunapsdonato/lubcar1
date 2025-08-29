package com.example.lubcar1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lubcar1.data.TrocaOleo
import com.example.lubcar1.uistates.EstadoTrocaOleo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.lubcar1.data.Cliente
import com.example.lubcar1.data.ClienteRepositorio
import com.example.lubcar1.data.TrocaOleoComInfo
import com.example.lubcar1.data.TrocaOleoRepositorio
import com.example.lubcar1.data.Veiculo
import kotlinx.coroutines.flow.collect
import com.example.lubcar1.data.VeiculoRepositorio
import kotlinx.coroutines.flow.asStateFlow
import com.example.lubcar1.data.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn


class TrocaOleoViewModel(
    private val clienteRepositorio: ClienteRepositorio,
    private val veiculoRepositorio: VeiculoRepositorio,
    private val trocaOleoRepositorio: TrocaOleoRepositorio
) : ViewModel() {

    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes.asStateFlow()

    private val _veiculos = MutableStateFlow<List<Veiculo>>(emptyList())
    val veiculos: StateFlow<List<Veiculo>> = _veiculos.asStateFlow()

    private val _estado = MutableStateFlow(EstadoTrocaOleo())
    val estado: StateFlow<EstadoTrocaOleo> = _estado.asStateFlow()

    private val _trocas = MutableStateFlow<List<TrocaOleo>>(emptyList())
    val trocas: StateFlow<List<TrocaOleo>> = _trocas.asStateFlow()

    val trocasComInfo: StateFlow<List<TrocaOleoComInfo>> =
        combine(_trocas, _clientes, _veiculos) { trocas, clientes, veiculos ->
            trocas.mapNotNull { troca ->
                val cliente = clientes.find { it.id == troca.idCliente }
                val veiculo = veiculos.find { it.id == troca.idVeiculo }

                if (cliente != null && veiculo != null) {
                    TrocaOleoComInfo(
                        troca = troca,
                        clienteNome = cliente.nome,
                        clienteSobrenome = cliente.sobrenome,
                        veiculoModelo = veiculo.modelo,
                        veiculoPlaca = veiculo.placa
                    )
                } else null
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            clienteRepositorio.listarTodos().collect { _clientes.value = it }
        }
        viewModelScope.launch {
            veiculoRepositorio.listarTodos().collect { _veiculos.value = it }
        }
    }

    fun aoAlterarCampo(campo: String, valor: String) {
        _estado.value = when (campo) {
            "data" -> _estado.value.copy(data = valor)
            "quilometragem" -> _estado.value.copy(quilometragem = valor)
            "tipoOleo" -> _estado.value.copy(tipoOleo = valor)
            "observacoes" -> _estado.value.copy(observacoes = valor)
            else -> _estado.value
        }
    }

    fun onClienteSelecionado(cliente: Cliente) {
        _estado.value = _estado.value.copy(clienteSelecionado = cliente)
    }

    fun onVeiculoSelecionado(veiculo: Veiculo) {
        _estado.value = _estado.value.copy(veiculoSelecionado = veiculo)
        carregarTrocas(veiculo.id)
    }

    fun registrarTroca() {
        val km = _estado.value.quilometragem.toIntOrNull()
        val cliente = _estado.value.clienteSelecionado
        val veiculo = _estado.value.veiculoSelecionado

        if (_estado.value.data.isBlank() || _estado.value.tipoOleo.isBlank() || km == null || cliente == null || veiculo == null) {
            _estado.value = _estado.value.copy(erro = "Preencha todos os campos corretamente")
            return
        }

        viewModelScope.launch {
            try {
                _estado.value = _estado.value.copy(carregando = true)
                val troca = TrocaOleo(
                    idCliente = cliente.id,
                    idVeiculo = veiculo.id,
                    data = _estado.value.data,
                    quilometragem = km,
                    tipoOleo = _estado.value.tipoOleo,
                    observacoes = _estado.value.observacoes
                )
                trocaOleoRepositorio.registrarTroca(troca)
                _estado.value = EstadoTrocaOleo(sucesso = true)
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(erro = e.message, carregando = false)
            }
        }
    }

    fun carregarTrocas(idVeiculo: Long) {
        viewModelScope.launch {
            try {
                _trocas.value = trocaOleoRepositorio.listarPorVeiculo(idVeiculo)
            } catch (e: Exception) {
                _estado.value = _estado.value.copy(erro = "Erro ao carregar histórico")
            }
        }
    }

    fun limparCampos() {
        _estado.value = EstadoTrocaOleo()
    }

    fun resetarEstado() {
        _estado.value = _estado.value.copy(sucesso = false, erro = null, carregando = false)
    }
}