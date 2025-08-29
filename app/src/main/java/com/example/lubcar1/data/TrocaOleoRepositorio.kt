package com.example.lubcar1.data

class TrocaOleoRepositorio(private val dao: TrocaOleoDao) {
    suspend fun obterTrocas(idVeiculo: Long) = dao.buscarPorVeiculo(idVeiculo)

    suspend fun registrarTroca(troca: TrocaOleo) = dao.registrarTroca(troca)

    suspend fun listarPorVeiculo(idVeiculo: Long) = dao.listarPorVeiculo(idVeiculo)

}