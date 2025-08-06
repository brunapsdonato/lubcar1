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
import com.example.lubcar1.model.Cliente
import com.example.lubcar1.model.ClienteDao

import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroCliente(navController: NavHostController) {
    val context = LocalContext.current
    val repo = remember { ClienteDao() }
    val scope = rememberCoroutineScope()

    var nome by remember { mutableStateOf("") }
    var sobrenome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var mensagem by remember { mutableStateOf("") }

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
                    val sucesso = repo.adicionar(novoCliente)
                    if (sucesso) {
                        mensagem = "Cliente cadastrado com sucesso!"
                        nome = ""
                        sobrenome = ""
                        telefone = ""
                        email = ""
                        cpf = ""
                        navController.navigate("home")
                    } else {
                        mensagem = "Erro ao cadastrar cliente."
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Salvar Cliente")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("home") }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Voltar ao Menu")
        }

        if (mensagem.isNotEmpty()) {
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
            mensagem = ""
        }
    }
}