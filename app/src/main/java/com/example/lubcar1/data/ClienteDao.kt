package com.example.lubcar1.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun adicionar(cliente: Cliente)

    @Update
    suspend fun editar(cliente: Cliente): Int

    @Delete
    suspend fun remover(cliente: Cliente)

    @Query("SELECT * FROM cliente")
    suspend fun listar(): List<Cliente>

    @Query("SELECT * FROM cliente")
    fun listarTodos(): Flow<List<Cliente>>

    @Query("SELECT * FROM cliente WHERE id = :id")
    suspend fun buscarPorId(id: Long): Cliente?
}