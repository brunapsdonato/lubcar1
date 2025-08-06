package com.example.lubcar1.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.toObject

class VeiculoDao {
    val clienteDao = ClienteDao()

    private val db = FirebaseFirestore.getInstance()
    private val veiculosRef = db.collection("veiculos")

    // CREATE: adicionar veículo
    suspend fun adicionar(veiculo: Veiculo): Boolean {
        return try {
            veiculosRef.add(veiculo).await()
            println("Veículo adicionado com sucesso!")
            true
        } catch (e: Exception) {
            println("Erro ao adicionar veículo: $e")
            false
        }
    }

    // READ: listar veículos
    suspend fun listar(): List<Veiculo> {
        return try {
            val snapshot = veiculosRef.get().await()
            snapshot.documents.mapNotNull { doc ->
                val veiculo = doc.toObject<Veiculo>()
                var cliente = clienteDao.buscarPorId(veiculo?.clienteId ?: "")
                veiculo?.clienteNome = cliente?.nome + cliente?.sobrenome
                veiculo?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            println("Erro ao listar veículos: $e")
            emptyList()
        }
    }

    // UPDATE: editar veículo
    suspend fun editar(veiculo: Veiculo): Boolean {
        return try {
            veiculosRef.document(veiculo.id).set(veiculo).await()
            println("Veículo editado com sucesso!")
            true
        } catch (e: Exception) {
            println("Erro ao editar veículo: $e")
            false
        }
    }

    // DELETE: remover veículo
    suspend fun remover(veiculo: Veiculo): Boolean {
        return try {
            veiculosRef.document(veiculo.id).delete().await()
            println("Veículo removido com sucesso!")
            true
        } catch (e: Exception) {
            println("Erro ao remover veículo: $e")
            false
        }
    }

    suspend fun buscarPorId(id: String): Veiculo? {
        return try {
            val doc = veiculosRef.document(id).get().await()
            val veiculo = doc.toObject<Veiculo>()

            veiculo?.let {
                val cliente = clienteDao.buscarPorId(it.clienteId)
                val nomeCompleto = "${cliente?.nome ?: ""} ${cliente?.sobrenome ?: ""}".trim()
                it.copy(id = doc.id, clienteNome = nomeCompleto)
            }
        } catch (e: Exception) {
            println("Erro ao buscar veículo por ID: $e")
            null
        }
    }

    suspend fun listarPorCliente(clienteId: String): List<Veiculo> {
        return try {
            val snapshot = veiculosRef.whereEqualTo("clienteId", clienteId).get().await()
            snapshot.documents.mapNotNull { doc ->
                val veiculo = doc.toObject<Veiculo>()
                veiculo?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            println("Erro ao listar veículos por cliente: $e")
            emptyList()
        }
    }
}