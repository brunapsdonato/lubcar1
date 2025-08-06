package com.example.lubcar1.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lubcar1.model.Cliente
import com.example.lubcar1.model.ClienteDao
import com.example.lubcar1.model.Veiculo
import com.example.lubcar1.model.VeiculoDao
import android.widget.Toast
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaEditarVeiculo(navController: NavHostController, veiculoId: String) {
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
        val veiculo = veiculoRepo.buscarPorId(veiculoId)
        listaClientes = clienteRepo.listar()
        modelo = veiculo?.modelo ?: ""
        placa = veiculo?.placa ?: ""
        ano = veiculo?.ano?.toString() ?: ""
        tipoDeOleo = veiculo?.tipoDeOleo ?: ""
        clienteSelecionado = veiculo?.clienteId?.let { clienteRepo.buscarPorId(it) }
    }

    val nomeClienteSelecionado = clienteSelecionado?.let { "${it.nome} ${it.sobrenome}" } ?: ""

    Column(modifier = Modifier.padding(32.dp)) {
        Text("Editar Veículo", style = MaterialTheme.typography.titleLarge)

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

        ExposedDropdownMenuBox(expanded = menuAberto, onExpandedChange = { menuAberto = !menuAberto }) {
            OutlinedTextField(
                value = nomeClienteSelecionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Cliente") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuAberto) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = menuAberto, onDismissRequest = { menuAberto = false }) {
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

        Button(onClick = {
            scope.launch {
                val veiculoEditado = Veiculo(
                    id = veiculoId,
                    modelo = modelo,
                    placa = placa,
                    ano = ano.toIntOrNull() ?: 0,
                    tipoDeOleo = tipoDeOleo,
                    clienteId = clienteSelecionado?.id ?: ""
                )
                val sucesso = veiculoRepo.editar(veiculoEditado)
                mensagem = if (sucesso) "Veículo editado com sucesso!" else "Erro ao editar veículo."
                if (sucesso) navController.navigate("home")
            }
        }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Salvar Alterações")
        }

        if (mensagem.isNotEmpty()) {
            Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show()
            mensagem = ""
        }
    }
}