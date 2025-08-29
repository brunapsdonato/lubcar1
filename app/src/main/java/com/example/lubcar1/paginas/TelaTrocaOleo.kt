package com.example.lubcar1.paginas

import com.example.lubcar1.paginas.TrocaDeOleoCard
import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lubcar1.viewmodels.TrocaOleoViewModel
import kotlinx.coroutines.launch
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaTrocaOleo(navController: NavHostController, viewModel: TrocaOleoViewModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val estado by viewModel.estado.collectAsState()
    val trocas by viewModel.trocas.collectAsState()
    val trocasComInfo by viewModel.trocasComInfo.collectAsState()
    val listaClientes by viewModel.clientes.collectAsState()
    val listaVeiculos by viewModel.veiculos.collectAsState()

    var menuClienteAberto by remember { mutableStateOf(false) }
    var menuVeiculoAberto by remember { mutableStateOf(false) }
    var menuOleoAberto by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val datePicker = remember {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                val dataFormatada = "%02d/%02d/%04d".format(day, month + 1, year)
                viewModel.aoAlterarCampo("data", dataFormatada)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    val tiposDeOleo = listOf("Sintético", "Semissintético", "Mineral")

    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Registro de Troca de Óleo", style = MaterialTheme.typography.titleLarge)

        // Seleção de Cliente
        Text("Selecione o Cliente", style = MaterialTheme.typography.titleMedium)
        ExposedDropdownMenuBox(
            expanded = menuClienteAberto,
            onExpandedChange = { menuClienteAberto = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = estado.clienteSelecionado?.let { "${it.nome} ${it.sobrenome}" } ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Cliente") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuClienteAberto) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = menuClienteAberto,
                onDismissRequest = { menuClienteAberto = false }
            ) {
                listaClientes.forEach { cliente ->
                    DropdownMenuItem(
                        text = { Text("${cliente.nome} ${cliente.sobrenome}") },
                        onClick = {
                            viewModel.onClienteSelecionado(cliente)
                            menuClienteAberto = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        // Seleção de Veículo
        Text("Selecione o Veículo", style = MaterialTheme.typography.titleMedium)
        ExposedDropdownMenuBox(
            expanded = menuVeiculoAberto,
            onExpandedChange = { menuVeiculoAberto = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = estado.veiculoSelecionado?.modelo ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Veículo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuVeiculoAberto) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = menuVeiculoAberto,
                onDismissRequest = { menuVeiculoAberto = false }
            ) {
                listaVeiculos.forEach { veiculo ->
                    DropdownMenuItem(
                        text = { Text("${veiculo.modelo} - ${veiculo.placa}") },
                        onClick = {
                            viewModel.onVeiculoSelecionado(veiculo)
                            menuVeiculoAberto = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = { datePicker.show() }, modifier = Modifier.fillMaxWidth()) {
            Text(if (estado.data.isBlank()) "Selecionar Data" else "Data: ${estado.data}")
        }

        Spacer(Modifier.height(8.dp))

        // Tipo de óleo
        ExposedDropdownMenuBox(
            expanded = menuOleoAberto,
            onExpandedChange = { menuOleoAberto = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = estado.tipoOleo,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de óleo") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuOleoAberto) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = menuOleoAberto,
                onDismissRequest = { menuOleoAberto = false }
            ) {
                tiposDeOleo.forEach { tipo ->
                    DropdownMenuItem(
                        text = { Text(tipo) },
                        onClick = {
                            viewModel.aoAlterarCampo("tipoOleo", tipo)
                            menuOleoAberto = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = estado.quilometragem,
            onValueChange = { viewModel.aoAlterarCampo("quilometragem", it) },
            label = { Text("Quilometragem") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.observacoes,
            onValueChange = { viewModel.aoAlterarCampo("observacoes", it) },
            label = { Text("Observações") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                scope.launch {
                    viewModel.registrarTroca()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Salvar Troca")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("home") }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Voltar ao Menu")
        }

        when {
            estado.sucesso -> {
                Toast.makeText(context, "Troca registrada com sucesso!", Toast.LENGTH_SHORT).show()
                viewModel.limparCampos()
                viewModel.resetarEstado()
            }
            estado.erro != null -> {
                Toast.makeText(context, "Erro: ${estado.erro}", Toast.LENGTH_SHORT).show()
                viewModel.resetarEstado()
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Histórico de Trocas", style = MaterialTheme.typography.titleMedium)

        trocasComInfo.forEach { trocaInfo ->
            TrocaDeOleoCard(trocaInfo = trocaInfo)
        }

    }
}