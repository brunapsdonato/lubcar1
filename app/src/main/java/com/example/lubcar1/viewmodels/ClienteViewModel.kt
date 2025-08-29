package com.example.lubcar1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lubcar1.data.Cliente
import com.example.lubcar1.data.ClienteRepositorio
import com.example.lubcar1.uistates.EstadoUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class ClienteViewModel(private val repositorio: ClienteRepositorio) : ViewModel() {

    private val _clientes = MutableStateFlow<List<Cliente>>(emptyList())
    val clientes: StateFlow<List<Cliente>> = _clientes.asStateFlow()

    private val _estado = MutableStateFlow<EstadoUI>(EstadoUI.Idle)
    val estado: StateFlow<EstadoUI> = _estado.asStateFlow()

    init {
        viewModelScope.launch {
            _estado.value = EstadoUI.Carregando
            try {
                repositorio.listarTodos().collect { listaClientes ->
                    _clientes.value = listaClientes
                    _estado.value = EstadoUI.Idle
                }
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro(e.message ?: "Erro ao carregar clientes")
            }
        }
    }

    suspend fun buscarPorId(id: Long): Cliente? {
        return try {
            repositorio.buscarPorId(id)
        } catch (e: Exception) {
            null
        }
    }

    fun adicionarCliente(cliente: Cliente) {
        viewModelScope.launch {
            try {
                repositorio.adicionar(cliente)
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro(e.message ?: "Erro ao adicionar cliente")
            }
        }
    }

    fun removerCliente(cliente: Cliente) {
        viewModelScope.launch {
            try {
                repositorio.remover(cliente)
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro(e.message ?: "Erro ao remover cliente")
            }
        }
    }

    fun editarCliente(cliente: Cliente) {
        viewModelScope.launch {
            try {
                repositorio.editar(cliente)
                _estado.value = EstadoUI.Sucesso
            } catch (e: Exception) {
                _estado.value = EstadoUI.Erro(e.message ?: "Erro ao editar cliente")
            }
        }
    }

    fun resetarEstado() {
        _estado.value = EstadoUI.Idle
    }
}