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
import com.example.lubcar1.viewmodels.ClienteViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListarCliente(navController: NavHostController) {
    val viewModel: ClienteViewModel = koinViewModel()
    val listaClientes by viewModel.clientes.collectAsState()
    val estado by viewModel.estado.collectAsState()

    LaunchedEffect(Unit) {
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Lista de Clientes") })
        }
    ) { padding ->
        if (estado == com.example.lubcar1.uistates.EstadoUI.Carregando) {
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
                    items(listaClientes) { cliente ->
                        ClienteCard(
                            cliente = cliente,
                            onRemoverConfirmado = { clienteRemovido ->
                                viewModel.removerCliente(clienteRemovido)
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