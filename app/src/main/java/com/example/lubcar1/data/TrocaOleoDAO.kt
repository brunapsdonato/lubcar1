package com.example.lubcar1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TrocaOleoDao {
    @Insert
    suspend fun registrarTroca(troca: TrocaOleo)

    @Query("SELECT * FROM troca_oleo WHERE idVeiculo = :idVeiculo ORDER BY data DESC")
    suspend fun listarPorVeiculo(idVeiculo: Long): List<TrocaOleo>

    @Query("SELECT * FROM troca_oleo WHERE idVeiculo = :idVeiculo")
    suspend fun buscarPorVeiculo(idVeiculo: Long): List<TrocaOleo>
}