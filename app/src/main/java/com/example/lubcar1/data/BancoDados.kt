package com.example.lubcar1.data

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [TrocaOleo::class, Cliente::class, Veiculo::class],
    version = 1,
    exportSchema = false
)

abstract class BancoDados : RoomDatabase() {
    abstract fun trocaOleoDao(): TrocaOleoDao
    abstract fun veiculoDao(): VeiculoDao
    abstract fun clienteDao(): ClienteDao
}