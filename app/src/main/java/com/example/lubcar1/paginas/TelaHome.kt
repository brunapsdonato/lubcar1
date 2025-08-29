package com.example.lubcar1.paginas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("LubCar - Menu Principal", style = MaterialTheme.typography.headlineMedium)

        HomeCard("Cadastro de Cliente") { navController.navigate("cliente") }
        HomeCard("Listagem de Clientes") { navController.navigate("listaClientes") }
        HomeCard("Cadastro de Veículo") { navController.navigate("veiculo") }
        HomeCard("Listagem de Veículos") { navController.navigate("listaVeiculos") }
        HomeCard("Registro de Troca de Óleo") { navController.navigate("registroTrocaOleo") }
    }
}