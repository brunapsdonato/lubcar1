package com.example.lubcar1.uistates

sealed class EstadoUI {
    object Idle : EstadoUI()
    object Carregando : EstadoUI()
    object Sucesso : EstadoUI()
    data class Erro(val mensagem: String) : EstadoUI()
}