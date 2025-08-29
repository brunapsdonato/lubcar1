package com.example.lubcar1.data

class ClienteRepositorio(private val dao: ClienteDao) {

    suspend fun adicionar(cliente: Cliente) = dao.adicionar(cliente)
    suspend fun editar(cliente: Cliente): Boolean {
        return dao.editar(cliente) > 0
    }
    suspend fun remover(cliente: Cliente) = dao.remover(cliente)

    fun listarTodos() = dao.listarTodos()

    suspend fun buscarPorId(id: Long) = dao.buscarPorId(id)
}