package com.example.lubcar1.paginas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lubcar1.data.TrocaOleoComInfo

@Composable
fun TrocaDeOleoCard(trocaInfo: TrocaOleoComInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("📅 Data: ${trocaInfo.troca.data}", style = MaterialTheme.typography.titleMedium)
            Text("🛢️ Tipo de Óleo: ${trocaInfo.troca.tipoOleo}", style = MaterialTheme.typography.bodyMedium)
            Text("🚗 Quilometragem: ${trocaInfo.troca.quilometragem}", style = MaterialTheme.typography.bodyMedium)
            trocaInfo.troca.observacoes?.takeIf { it.isNotBlank() }?.let {
                Spacer(modifier = Modifier.height(4.dp))
                Text("📝 Observações: $it", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("👤 Cliente: ${trocaInfo.clienteNome} ${trocaInfo.clienteSobrenome}", style = MaterialTheme.typography.bodyMedium)
            Text("🚙 Veículo: ${trocaInfo.veiculoModelo} - ${trocaInfo.veiculoPlaca}", style = MaterialTheme.typography.bodySmall)
        }
    }
}