package com.example.lubcar1.paginas

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lubcar1.model.Cliente
import com.example.lubcar1.model.ClienteDao
import com.example.lubcar1.model.Veiculo
import com.example.lubcar1.model.VeiculoDao
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroVeiculo(navController: NavHostController) {
    val context = LocalContext.current
    val veiculoRepo = remember { VeiculoDao() }
    val clienteRepo = remember { ClienteDao() }
    val scope = rememberCoroutineScope()

    var modelo by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }
    var tipoDeOleo by remember { mutableStateOf("") }

    var clienteSelecionado by remember { mutableStateOf<Cliente?>(null) }
    var listaClientes by remember { mutableStateOf(listOf<Cliente>()) }
    var mensagem by remember { mutableStateOf("") }

    var menuAberto by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        listaClientes = clienteRepo.listar()
    }

    val nomeClienteSelecionado = clienteSelecionado?.let { "${it.nome} ${it.sobrenome}" } ?: ""

    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cadastro de Veículo", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(value = modelo, onValueChange = { modelo = it },
            label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = placa, onValueChange = { placa = it },
            label = { Text("Placa") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = ano, onValueChange = { ano = it },
            label = { Text("Ano") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = tipoDeOleo, onValueChange = { tipoDeOleo = it },
            label = { Text("Tipo de Óleo") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Text("Selecione um Cliente", style = MaterialTheme.typography.titleMedium)

        ExposedDropdownMenuBox(
            expanded = menuAberto,
            onExpandedChange = { menuAberto = !menuAberto },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = nomeClienteSelecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Cliente") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAberto) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = menuAberto,
                onDismissRequest = { menuAberto = false }
            ) {
                listaClientes.forEach { cliente ->
                    DropdownMenuItem(
                        text = { Text("${cliente.nome} ${cliente.sobrenome}") },
                        onClick = {
                            clienteSelecionado = cliente
                            menuAberto = false
                        }
                    )
                }
            }
        }

        clienteSelecionado?.let {
            Text(
                "Cliente selecionado: ${it.nome} ${it.sobrenome}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                scope.launch {
                    val veiculo = Veiculo(
                        modelo = modelo,
                        placa = placa,
                        ano = ano.toIntOrNull() ?: 0,
                        tipoDeOleo = tipoDeOleo,
                        clienteId = clienteSelecionado?.id ?: ""
                    )
                    val sucesso = veiculoRepo.adicionar(veiculo)
                    if (sucesso) {
                        mensagem = "Veículo cadastrado com sucesso!"
                        modelo = ""
                        placa = ""
                        ano = ""
                        tipoDeOleo = ""
                        clienteSelecionado = null
                        navController.navigate("home")
                    } else {
                        mensagem = "Erro ao cadastrar veículo."
                    }
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Salvar Veículo")
        }

        Button(onClick = { navController.navigate("home") }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Voltar ao Menu")
        }

        if (mensagem.isNotEmpty()) {
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
            mensagem = ""
        }
    }
}