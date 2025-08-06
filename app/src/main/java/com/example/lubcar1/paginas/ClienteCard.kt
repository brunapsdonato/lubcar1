package com.example.lubcar1.paginas

import androidx.compose.foundation.clickable
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
import com.example.lubcar1.model.Cliente

@Composable
fun ClienteCard(
    cliente: Cliente,
    onRemoverConfirmado: (Cliente) -> Unit,
    navController: NavController
) {
    var mostrarDialog by remember { mutableStateOf(false) }

    if (mostrarDialog) {
        AlertDialog(
            onDismissRequest = { mostrarDialog = false },
            title = { Text("Remover Cliente") },
            text = { Text("Tem certeza que deseja remover \"${cliente.nome}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialog = false
                    onRemoverConfirmado(cliente)
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
                Text(text = cliente.nome, style = MaterialTheme.typography.titleMedium)
                Text(text = "Sobrenome: ${cliente.sobrenome}",
                    style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Text(text = "Telefone: ${cliente.telefone}",
                    style = MaterialTheme.typography.bodySmall)
                Text(text = "Email: ${cliente.email}",
                    style = MaterialTheme.typography.bodySmall)
                Text(text = "CPF: ${cliente.cpf}",
                    style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            }

            Row {
                IconButton(
                    onClick = {
                        navController.navigate("telaEditarCliente/${cliente.id}")
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar Cliente",
                    )
                }

                IconButton(
                    onClick = { mostrarDialog = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar Cliente"
                    )
                }
            }
        }
    }
}
