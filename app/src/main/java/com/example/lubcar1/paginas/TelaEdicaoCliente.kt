package com.example.lubcar1.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.lubcar1.model.Cliente
import com.example.lubcar1.model.ClienteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TelaEditarCliente(
    clienteId: String,
    navController: NavController
) {
    val dao = ClienteDao()
    var cliente by remember { mutableStateOf<Cliente?>(null) }
    var carregando by remember { mutableStateOf(true) }

    val nome = remember { mutableStateOf("") }
    val sobrenome = remember { mutableStateOf("") }
    val telefone = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }

    LaunchedEffect(clienteId) {
        val c = dao.buscarPorId(clienteId)
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
        cliente?.let {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Editar Cliente", style = MaterialTheme.typography.headlineSmall)

                OutlinedTextField(value = nome.value, onValueChange = { nome.value = it }, label = { Text("Nome") })
                OutlinedTextField(value = sobrenome.value, onValueChange = { sobrenome.value = it }, label = { Text("Sobrenome") })
                OutlinedTextField(value = telefone.value, onValueChange = { telefone.value = it }, label = { Text("Telefone") })
                OutlinedTextField(value = email.value, onValueChange = { email.value = it }, label = { Text("Email") })
                OutlinedTextField(value = cpf.value, onValueChange = { cpf.value = it }, label = { Text("CPF") })

                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val clienteEditado = it.copy(
                            nome = nome.value,
                            sobrenome = sobrenome.value,
                            telefone = telefone.value,
                            email = email.value,
                            cpf = cpf.value
                        )
                        val sucesso = dao.editar(clienteEditado)
                        if (sucesso) {
                            withContext(Dispatchers.Main) {
                                navController.popBackStack()
                            }
                        }
                    }
                }) {
                    Text("Salvar")
                }
            }
        } ?: Text("Cliente não encontrado.")
    }
}