package com.example.lubcar1.viewmodels

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TrocaOleoViewModel(get(), get(), get()) }
    viewModel { ClienteViewModel(get()) }
    viewModel { VeiculoViewModel(get(),get()) }
}