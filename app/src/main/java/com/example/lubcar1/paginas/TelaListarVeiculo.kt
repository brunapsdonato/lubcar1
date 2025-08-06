package com.example.lubcar1.paginas

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lubcar1.model.Veiculo
import com.example.lubcar1.model.VeiculoDao
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListarVeiculo(navController: NavHostController) {
    val dao = VeiculoDao()
    var listaVeiculos by remember { mutableStateOf(listOf<Veiculo>()) }
    var carregando by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        listaVeiculos = dao.listar()
        carregando = false
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Veículos") })
        }
    ) { padding ->
        if (carregando) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                                listaVeiculos = listaVeiculos - veiculoRemovido
                                coroutineScope.launch {
                                    dao.remover(veiculoRemovido)
                                }
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
        }
    }
}