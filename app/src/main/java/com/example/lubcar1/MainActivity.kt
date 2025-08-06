package com.example.lubcar1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.lubcar1.paginas.TelaCadastroCliente
import com.example.lubcar1.paginas.TelaHome
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.lubcar1.ui.theme.LubcarTheme
import com.example.lubcar1.paginas.TelaCadastroVeiculo
import com.example.lubcar1.paginas.TelaEditarCliente
import com.example.lubcar1.paginas.TelaListarCliente
import com.example.lubcar1.paginas.LoginScreen
import com.example.lubcar1.paginas.TelaEditarVeiculo
import com.example.lubcar1.paginas.TelaListarVeiculo


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LubcarTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("home") { TelaHome(navController) }
                    composable("cliente") { TelaCadastroCliente(navController) }
                    composable("veiculo") { TelaCadastroVeiculo(navController) }
                    composable("listaClientes") { TelaListarCliente(navController) }
                    composable("listaVeiculos") { TelaListarVeiculo(navController) }


                    composable("telaEditarCliente/{clienteId}") { backStackEntry ->
                        val clienteId = backStackEntry.arguments?.getString("clienteId") ?: ""
                        TelaEditarCliente(clienteId = clienteId, navController = navController)
                    }

                    composable("telaEditarVeiculo/{veiculoId}") { backStackEntry ->
                        val veiculoId = backStackEntry.arguments?.getString("veiculoId") ?: ""
                        TelaEditarVeiculo(veiculoId = veiculoId, navController = navController)
                    }
                }
            }
        }
    }
}