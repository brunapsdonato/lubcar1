package com.example.lubcar1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface VeiculoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun adicionar(veiculo: Veiculo)


    @Query("SELECT * FROM veiculo")
    suspend fun listar(): List<Veiculo>

    @Update
    suspend fun editar(veiculo: Veiculo)

    @Delete
    suspend fun remover(veiculo: Veiculo)

    @Query("SELECT * FROM veiculo")
    fun listarTodos(): Flow<List<Veiculo>>

    @Query("SELECT * FROM veiculo WHERE id = :id")
    suspend fun buscarPorId(id: Long): Veiculo?

    @Query("SELECT * FROM veiculo WHERE clienteId = :clienteId")
    fun listarPorCliente(clienteId: Long): Flow<List<Veiculo>>

}