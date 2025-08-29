package com.example.lubcar1.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lubcar1.data.Cliente
import com.example.lubcar1.viewmodels.ClienteViewModel
import org.koin.androidx.compose.koinViewModel
import kotlinx.coroutines.launch

@Composable
fun TelaEditarCliente(
    clienteId: Long,
    navController: NavController
) {
    val viewModel: ClienteViewModel = koinViewModel()
    val scope = rememberCoroutineScope()

    var carregando by remember { mutableStateOf(true) }
    var cliente by remember { mutableStateOf<Cliente?>(null) }

    val nome = remember { mutableStateOf("") }
    val sobrenome = remember { mutableStateOf("") }
    val telefone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }

    LaunchedEffect(clienteId) {
        val c = viewModel.buscarPorId(clienteId)
        cliente = c
        c?.let {
            nome.value = it.nome
            sobrenome.value = it.sobrenome
            telefone.value = it.telefone
            email.value = it.email
            cpf.value = it.cpf
        }
        carregando = false
    }

    if (carregando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        cliente?.let { clienteOriginal ->
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Editar Cliente", style = MaterialTheme.typography.headlineSmall)

                OutlinedTextField(value = nome.value, onValueChange = { nome.value = it }, label = { Text("Nome") })
                OutlinedTextField(value = sobrenome.value, onValueChange = { sobrenome.value = it }, label = { Text("Sobrenome") })
                OutlinedTextField(value = telefone.value, onValueChange = { telefone.value = it }, label = { Text("Telefone") })
                OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
                OutlinedTextField(value = cpf.value, onValueChange = { cpf.value = it }, label = { Text("CPF") })

                Button(
                    onClick = {
                        scope.launch {
                            val clienteEditado = clienteOriginal.copy(
                                nome = nome.value,
                                sobrenome = sobrenome.value,
                                telefone = telefone.value,
                                email = email.value,
                                cpf = cpf.value
                            )
                            viewModel.editarCliente(clienteEditado)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Salvar")
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cliente não encontrado.")
        }
    }
}