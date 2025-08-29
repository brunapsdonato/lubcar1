package com.example.lubcar1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.navigation.compose.rememberNavController
import com.example.lubcar1.paginas.TelaCadastroCliente
import com.example.lubcar1.paginas.TelaHome
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lubcar1.ui.theme.LubcarTheme
import com.example.lubcar1.paginas.TelaCadastroVeiculo
import com.example.lubcar1.paginas.TelaEditarCliente
import com.example.lubcar1.paginas.TelaListarCliente
import com.example.lubcar1.paginas.TelaEditarVeiculo
import com.example.lubcar1.paginas.TelaListarVeiculo
import com.example.lubcar1.paginas.TelaTrocaOleo
import com.example.lubcar1.viewmodels.TrocaOleoViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LubcarTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { TelaHome(navController) }
                    composable("cliente") { TelaCadastroCliente(navController) }
                    composable("veiculo") { TelaCadastroVeiculo(navController) }
                    composable("listaClientes") { TelaListarCliente(navController) }
                    composable("listaVeiculos") { TelaListarVeiculo(navController) }

                    composable("registroTrocaOleo") {
                        val viewModel = koinViewModel<TrocaOleoViewModel>()
                        TelaTrocaOleo(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }

                    composable("telaEditarCliente/{clienteId}") { backStackEntry ->
                        val clienteIdStr = backStackEntry.arguments?.getString("clienteId")
                        val clienteId = clienteIdStr?.toLongOrNull()

                        if (clienteId != null) {
                            TelaEditarCliente(clienteId = clienteId, navController = navController)
                        } else {
                            Text("ID do cliente inválido")
                        }
                    }

                    composable("telaEditarVeiculo/{veiculoId}") { backStackEntry ->
                        val veiculoIdStr = backStackEntry.arguments?.getString("veiculoId")
                        val veiculoId = veiculoIdStr?.toLongOrNull()

                        if (veiculoId != null) {
                            TelaEditarVeiculo(veiculoId = veiculoId, navController = navController)
                        } else {
                            Text("ID do veículo inválido")
                        }
                    }
                }
            }
        }
    }
}