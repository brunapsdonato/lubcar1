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
import com.example.lubcar1.viewmodels.VeiculoViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroVeiculo(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: VeiculoViewModel = koinViewModel()
    val formState by viewModel.formState.collectAsState()
    var menuAberto by remember { mutableStateOf(false) }
    val listaClientes by viewModel.clientes.collectAsState()

    val nomeClienteSelecionado = formState.clienteSelecionado?.let {
        "${it.nome} ${it.sobrenome}"
    } ?: ""

    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Cadastro de Veículo", style = MaterialTheme.typography.titleLarge)

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
                            viewModel.onClienteSelecionado(cliente)
                            menuAberto = false
                        }
                    )
                }
            }
        }

        formState.clienteSelecionado?.let {
            Text(
                "Cliente selecionado: ${it.nome} ${it.sobrenome}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            onClick = {
                viewModel.salvarVeiculo(
                    0L,
                    onSucesso = {
                        Toast.makeText(context, "Veículo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.navigate("home")
                    },
                    onErro = { msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                )
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Salvar Veículo")
        }

        Button(
            onClick = { navController.navigate("home") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Voltar ao Menu")
        }
    }
}