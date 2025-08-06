package com.example.lubcar1.paginas

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.lubcar1.ui.theme.AmareloPrincipal
import com.example.lubcar1.ui.theme.PretoTexto
import com.example.lubcar1.R
import com.example.lubcar1.model.LoginViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val carImage = painterResource(id = R.drawable.ic_car)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = carImage,
            contentDescription = "Carro",
            modifier = Modifier
                .size(350.dp)
                .padding(top = 90.dp, bottom = 16.dp)
        )


        Text(
            text = "Seja bem-vindo!",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Faça seu login para continuar.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AmareloPrincipal,
                unfocusedBorderColor = AmareloPrincipal,
                focusedLabelColor = AmareloPrincipal,
                unfocusedLabelColor = PretoTexto
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AmareloPrincipal,
                unfocusedBorderColor = AmareloPrincipal,
                focusedLabelColor = AmareloPrincipal,
                unfocusedLabelColor = PretoTexto
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.loginWithEmailAndPassword(email, password, onLoginSuccess)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank(),
            colors = ButtonDefaults.buttonColors(
                containerColor = AmareloPrincipal,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Entrar")
        }

        if (viewModel.errorMessage.value.isNotEmpty()) {
            Text(
                text = viewModel.errorMessage.value,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}