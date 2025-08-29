package com.example.lubcar1.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.widget.Toast
import com.example.lubcar1.uistates.EstadoUI
import com.example.lubcar1.viewmodels.VeiculoViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaEditarVeiculo(navController: NavHostController, veiculoId: Long) {
    val context = LocalContext.current
    val viewModel: VeiculoViewModel = koinViewModel()

    val formState by viewModel.formState.collectAsState()
    val clientes by viewModel.clientes.collectAsState()
    val estado by viewModel.estado.collectAsState()

    var menuAberto by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.carregarClientes()
        viewModel.carregarVeiculoPorId(veiculoId)
    }

    Column(modifier = Modifier.padding(32.dp)) {
        Text("Editar Veículo", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = formState.modelo,
            onValueChange = viewModel::onModeloChange,
            label = { Text("Modelo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = formState.placa,
            onValueChange = viewModel::onPlacaChange,
            label = { Text("Placa") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = formState.ano,
            onValueChange = viewModel::onAnoChange,
            label = { Text("Ano") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = formState.tipoDeOleo,
            onValueChange = viewModel::onTipoDeOleoChange,
            label = { Text("Tipo de Óleo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Selecione um Cliente", style = MaterialTheme.typography.titleMedium)

        ExposedDropdownMenuBox(
            expanded = menuAberto,
            onExpandedChange = { menuAberto = !menuAberto }
        ) {
            OutlinedTextField(
                value = formState.clienteSelecionado?.let { "${it.nome} ${it.sobrenome}" } ?: "",
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
                clientes.forEach { cliente ->
                    DropdownMenuItem(
                        text = { Text("${cliente.nome} ${cliente.sobrenome}") },
                        onClick = {
                            viewModel.onClienteSelecionado(cliente)
                            menuAberto = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                viewModel.salvarVeiculo(
                    veiculoId,
                    onSucesso = { navController.navigate("home") },
                    onErro = { msg -> Toast.makeText(context, msg, Toast.LENGTH_SHORT).show() }
                )
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Salvar Alterações")
        }

        if (estado is
                    EstadoUI.Erro) {
            val erro = (estado as EstadoUI.Erro).mensagem
            Toast.makeText(context, erro, Toast.LENGTH_SHORT).show()
        }
    }
}