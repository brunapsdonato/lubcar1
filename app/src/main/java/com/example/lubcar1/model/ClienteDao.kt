package com.example.lubcar1.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.google.firebase.firestore.toObject


class ClienteDao {
    private val db = FirebaseFirestore.getInstance()
    private val clientesRef = db.collection("clientes")

    // CREATE: adicionar cliente
    suspend fun adicionar(cliente: Cliente): Boolean {
        return try {
            clientesRef.add(cliente).await()
            println("Cliente adicionado com sucesso!")
            true
        } catch (e: Exception) {
            println("Erro ao adicionar cliente: $e")
            false
        }
    }

    // READ: listar clientes
    suspend fun listar(): List<Cliente> {
        return try {
            val snapshot = clientesRef.get().await()
            snapshot.documents.mapNotNull { doc ->
                val cliente = doc.toObject<Cliente>()
                cliente?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            println("Erro ao listar clientes: $e")
            emptyList()
        }
    }

    // UPDATE: editar cliente
    suspend fun editar(cliente: Cliente): Boolean {
        return try {
            clientesRef.document(cliente.id).set(cliente).await()
            println("Cliente editado com sucesso!")
            true
        } catch (e: Exception) {
            println("Erro ao editar cliente: $e")
            false
        }
    }

    // DELETE: remover cliente
    suspend fun remover(cliente: Cliente): Boolean {
        return try {
            clientesRef.document(cliente.id).delete().await()
            println("Cliente removido com sucesso!")
            true
        } catch (e: Exception) {
            println("Erro ao remover cliente: $e")
            false
        }
    }

    // READ: buscar cliente por ID
    suspend fun buscarPorId(id: String): Cliente? {
        return try {
            val doc = clientesRef.document(id).get().await()
            doc.toObject<Cliente>()?.copy(id = doc.id)
        } catch (e: Exception) {
            println("Erro ao buscar cliente por ID: $e")
            null
        }
    }
}