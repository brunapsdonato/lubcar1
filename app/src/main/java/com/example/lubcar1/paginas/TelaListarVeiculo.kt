package com.example.lubcar1.paginas

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lubcar1.viewmodels.VeiculoViewModel
import com.example.lubcar1.uistates.EstadoUI
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListarVeiculo(navController: NavHostController) {
    val viewModel: VeiculoViewModel = koinViewModel()
    val listaVeiculos = viewModel.veiculos.collectAsState().value
    val estado = viewModel.estado.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.carregarVeiculos()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Veículos") })
        }
    ) { padding ->
/*        if (estado == EstadoUI.Carregando) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else {*/
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(listaVeiculos) { veiculo ->
                        VeiculoCard(
                            veiculo = veiculo,
                            onRemoverConfirmado = { veiculoRemovido ->
                                viewModel.removerVeiculo(veiculoRemovido)
                            },
                            navController = navController
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("home") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Voltar ao Menu")
                }
            }
        //}
    }
}