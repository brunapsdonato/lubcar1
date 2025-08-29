package com.example.lubcar1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lubcar1.data.Cliente
import com.example.lubcar1.data.ClienteRepositorio
import com.example.lubcar1.data.Veiculo
import com.example.lubcar1.data.VeiculoRepositorio
import com.example.lubcar1.uistates.EstadoUI
import com.example.lubcar1.uistates.VeiculoFormState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch



class VeiculoViewModel(
    private val veiculoRepo: VeiculoRepositorio,
    private val clienteRepo: ClienteRepositorio
) : ViewModel() {

    private val _formState = MutableStateFlow(VeiculoFormState())
    val formState: StateFlow<VeiculoFormState> = _formState

    private val _veiculos = MutableStateFlow<List<Veiculo>>(emptyList())
    val veiculos: StateFlow<List<Veiculo>> = _veiculos.asStateFlow()

    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes.asStateFlow()

    private val _estado = MutableStateFlow<EstadoUI>(EstadoUI.Idle)
    val estado: StateFlow<EstadoUI> = _estado.asStateFlow()

    init {
        viewModelScope.launch {
            clienteRepo.listarTodos().collect { _clientes.value = it }
        }
    }

    fun carregarVeiculos() {
        viewModelScope.launch {
            _estado.value = EstadoUI.Carregando
            try {
                val lista = veiculoRepo.listarTodos()
                lista.collect { listaVeiculos : List<Veiculo> -> _veiculos.value = listaVeiculos }
                _estado.value = EstadoUI.Sucesso
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun carregarClientes() {
        viewModelScope.launch {
            try {
                val lista = clienteRepo.listarTodos()
                lista.collect { listaClientes : List<Cliente> -> _clientes.value = listaClientes }
                _formState.update { it.copy(listaClientes = _clientes.value) }
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro("Erro ao carregar clientes")
            }
        }
    }

    fun carregarVeiculoPorId(id: Long) {
        viewModelScope.launch {
            val veiculo = veiculoRepo.buscarPorId(id)
            val cliente = veiculo?.clienteId?.let { clienteRepo.buscarPorId(it) }
            _formState.value = VeiculoFormState(
                modelo = veiculo?.modelo ?: "",
                placa = veiculo?.placa ?: "",
                ano = veiculo?.ano?.toString() ?: "",
                tipoDeOleo = veiculo?.tipoDeOleo ?: "",
                clienteSelecionado = cliente
            )
        }
    }

    fun adicionarVeiculo(veiculo: Veiculo) {
        viewModelScope.launch {
            try {
                veiculoRepo.adicionar(veiculo)
                carregarVeiculos()
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro(e.message ?: "Falha ao adicionar")
            }
        }
    }

    fun removerVeiculo(veiculo: Veiculo) {
        viewModelScope.launch {
            try {
                veiculoRepo.remover(veiculo)
                carregarVeiculos()
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro(e.message ?: "Falha ao remover")
            }
        }
    }

    fun carregarVeiculoParaEdicao(veiculo: Veiculo) {
        _formState.value = VeiculoFormState(
            id = veiculo.id,
            modelo = veiculo.modelo,
            placa = veiculo.placa,
            ano = veiculo.ano.toString(),
            tipoDeOleo = veiculo.tipoDeOleo,
            clienteSelecionado = Cliente(veiculo.clienteId, veiculo.clienteNome)
        )
    }

    fun editarVeiculo(veiculo: Veiculo) {
        viewModelScope.launch {
            try {
                veiculoRepo.editar(veiculo)
                carregarVeiculos()
                _estado.value = EstadoUI.Sucesso
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro(e.message ?: "Erro ao editar veículo")
            }
        }
    }

    fun carregarVeiculosDoCliente(clienteId: Long) {
        viewModelScope.launch {
            try {
                val veiculos = veiculoRepo.listarPorCliente(clienteId)
                veiculos.collect { listaVeiculos : List<Veiculo> -> _veiculos.value = listaVeiculos }

                //_formState.update { it.copy(listaClientes = _veiculos.value) }
            } catch (e: Exception) {
            }
        }
    }

    fun salvarVeiculo(
        veiculoId: Long,
        onSucesso: () -> Unit,
        onErro: (String) -> Unit
    ) {
        viewModelScope.launch {
            val cliente = formState.value.clienteSelecionado
            val anoInt = formState.value.ano.toIntOrNull()

            if (cliente == null) {
                onErro("Selecione um cliente")
                return@launch
            }

            if (anoInt == null) {
                onErro("Ano inválido")
                return@launch
            }

            val veiculo = Veiculo(
                id = veiculoId,
                modelo = formState.value.modelo,
                placa = formState.value.placa,
                ano = anoInt,
                tipoDeOleo = formState.value.tipoDeOleo,
                clienteId = cliente.id,
                clienteNome = "${cliente.nome} ${cliente.sobrenome}"
            )

            try {
                if (veiculo.id == 0L) {
                    veiculoRepo.adicionar(veiculo)
                } else {
                    veiculoRepo.editar(veiculo)
                }

                carregarVeiculos()
                onSucesso()
            } catch (e: Exception) {
                onErro("Erro ao salvar veículo: ${e.message}")
            }
        }
    }

    fun onModeloChange(value: String) {
        _formState.value = _formState.value.copy(modelo = value)
    }

    fun onPlacaChange(value: String) {
        _formState.value = _formState.value.copy(placa = value)
    }

    fun onAnoChange(value: String) {
        _formState.value = _formState.value.copy(ano = value)
    }

    fun onTipoDeOleoChange(value: String) {
        _formState.value = _formState.value.copy(tipoDeOleo = value)
    }

    fun onClienteSelecionado(cliente: Cliente) {
        _formState.value = _formState.value.copy(clienteSelecionado = cliente)
    }
}