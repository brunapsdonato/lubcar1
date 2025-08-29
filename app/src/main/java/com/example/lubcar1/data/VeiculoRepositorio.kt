package com.example.lubcar1.data

class VeiculoRepositorio(private val dao: VeiculoDao) {

    suspend fun adicionar(veiculo: Veiculo) = dao.adicionar(veiculo)

    fun listarTodos() = dao.listarTodos()

    suspend fun editar(veiculo: Veiculo) = dao.editar(veiculo)

    suspend fun remover(veiculo: Veiculo) = dao.remover(veiculo)

    suspend fun buscarPorId(id: Long) = dao.buscarPorId(id)

    suspend fun listarPorCliente(clienteId: Long) = dao.listarPorCliente(clienteId)
}