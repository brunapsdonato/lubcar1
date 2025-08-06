package com.example.lubcar1.paginas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Icon
import androidx.navigation.NavController
import com.example.lubcar1.model.Veiculo

@Composable
fun VeiculoCard(
    veiculo: Veiculo,
    onRemoverConfirmado: (Veiculo) -> Unit,
    navController: NavController
) {
    var mostrarDialog by remember { mutableStateOf(false) }

    if (mostrarDialog) {
        AlertDialog(
            onDismissRequest = { mostrarDialog = false },
            title = { Text("Remover Veiculo") },
            text = { Text("Tem certeza que deseja remover \"${veiculo.modelo}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialog = false
                    onRemoverConfirmado(veiculo)
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = veiculo.modelo, style = MaterialTheme.typography.titleMedium)
                Text(text = "Placa: ${veiculo.placa}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text(text = "Ano: ${veiculo.ano}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Óleo: ${veiculo.tipoDeOleo}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Cliente: ${veiculo.clienteNome}", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }

            Row {
                IconButton(
                    onClick = {
                        navController.navigate("telaEditarVeiculo/${veiculo.id}")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar Veiculo",
                    )
                }

                IconButton(
                    onClick = { mostrarDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar Veiculo"
                    )
                }
            }
        }
    }
}
