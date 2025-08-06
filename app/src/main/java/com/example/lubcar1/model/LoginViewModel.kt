package com.example.lubcar1.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    var errorMessage = mutableStateOf("")

    fun loginWithEmailAndPassword(email: String, password: String, onLoginSuccess: () -> Unit) {
        errorMessage.value = ""

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onLoginSuccess()
                } else {
                    errorMessage.value = when (task.exception) {
                        is FirebaseAuthInvalidUserException -> "Usuário não encontrado."
                        is FirebaseAuthInvalidCredentialsException -> "Usuário ou senha incorreta."
                        else -> "Erro ao fazer login: ${task.exception?.message}"
                    }
                }
            }
    }
}