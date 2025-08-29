package com.example.lubcar1.paginas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lubcar1.data.Cliente
import com.example.lubcar1.uistates.EstadoUI
import com.example.lubcar1.viewmodels.ClienteViewModel

import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroCliente(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: ClienteViewModel = koinViewModel()

    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }

    val estado by viewModel.estado.collectAsState()
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cadastro de Cliente", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(value = nome, onValueChange = { nome = it },
            label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = sobrenome, onValueChange = { sobrenome = it },
            label = { Text("Sobrenome") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = telefone, onValueChange = { telefone = it },
            label = { Text("Telefone") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = email, onValueChange = { email = it },
            label = { Text("E-mail") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = cpf, onValueChange = { cpf = it },
            label = { Text("CPF") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                scope.launch {
                    val novoCliente = Cliente(
                        nome = nome,
                        sobrenome = sobrenome,
                        telefone = telefone,
                        email = email,
                        cpf = cpf
                    )
                    viewModel.adicionarCliente(novoCliente)
                    Toast.makeText(context, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    nome = ""; sobrenome = ""; telefone = ""; email = ""; cpf = ""
                    viewModel.resetarEstado()
                    navController.navigate("home")                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Salvar Cliente")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("home") }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Voltar ao Menu")
        }

        when (estado) {
            is EstadoUI.Sucesso -> {
                Toast.makeText(context, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                nome = ""; sobrenome = ""; telefone = ""; email = ""; cpf = ""
                viewModel.resetarEstado()
                navController.navigate("home")
            }
            is EstadoUI.Erro -> {
                val msg = (estado as EstadoUI.Erro).mensagem
                Toast.makeText(context, "Erro: $msg", Toast.LENGTH_SHORT).show()
                viewModel.resetarEstado()
            }
            else -> {}
        }
    }
}