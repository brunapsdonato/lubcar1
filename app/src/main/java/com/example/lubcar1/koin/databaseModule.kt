package com.example.lubcar1.koin

import com.example.lubcar1.data.BancoDados
import com.example.lubcar1.data.TrocaOleoRepositorio
import org.koin.dsl.module
import androidx.room.Room
import com.example.lubcar1.data.ClienteRepositorio
import com.example.lubcar1.data.VeiculoRepositorio
import org.koin.core.module.dsl.singleOf

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            BancoDados::class.java,
            "banco_oficina.db"
        ).build()
    }

    single { get<BancoDados>().trocaOleoDao() }
    single { get<BancoDados>().clienteDao() }
    single { get<BancoDados>().veiculoDao() }

    singleOf(::TrocaOleoRepositorio)
    singleOf(::ClienteRepositorio)
    singleOf(::VeiculoRepositorio)
}