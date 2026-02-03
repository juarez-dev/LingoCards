package com.juarez.lingocards.ui.screens.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegistered: () -> Unit,
    onCancel: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    //Estate of the values
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    // English note: your User entity requires email
    val email = remember { mutableStateOf("") }

    //Estate of the errors
    val usernameError = remember { mutableStateOf<String?>(null) }
    val passwordError = remember { mutableStateOf<String?>(null) }
    val confirmError = remember { mutableStateOf<String?>(null) }
    val emailError = remember { mutableStateOf<String?>(null) }

    //FocusRequester
    val (usernameFocusRequester, passwordFocusRequester, confirmFocusRequester) = FocusRequester.createRefs()
    val emailFocusRequester = remember { FocusRequester() }

    //KeyboardController -> to hide the keyboard at least
    val keyboardController = LocalSoftwareKeyboardController.current

    // English note: react to ViewModel success
    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            viewModel.consumeSuccess()
            onRegistered()
        }
    }

    // Sync VM errors -> local errors (keep your existing local error flow)
    LaunchedEffect(uiState.usernameError, uiState.passwordError, uiState.confirmError, uiState.emailError, uiState.generalError) {
        usernameError.value = uiState.usernameError
        passwordError.value = uiState.passwordError
        confirmError.value = uiState.confirmError
        emailError.value = uiState.emailError
        // If you want: show generalError somewhere (toast/snackbar)
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //User
        OutlinedTextField(
            value = username.value,
            onValueChange = {
                username.value = it
                if (it.isNotBlank()) usernameError.value = null
            },
            label = { Text("Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(usernameFocusRequester),
            isError = usernameError.value != null,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { passwordFocusRequester.requestFocus() })
        )
        if (usernameError.value != null) {
            Text(usernameError.value ?: "", color = Color.Red, modifier = Modifier.padding(top = 4.dp))
        }

        //Password
        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                if (it.isNotBlank()) passwordError.value = null
            },
            label = { Text("Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            isError = passwordError.value != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next), // Action Ime config
            keyboardActions = KeyboardActions(
                onNext = { //Change to the next focus
                    confirmFocusRequester.requestFocus()
                }
            )
        )

        //Password confirmation
        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = {
                confirmPassword.value = it
                if (it.isNotBlank()) confirmError.value = null
            },
            label = { Text("Repetir contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(confirmFocusRequester),
            isError = confirmError.value != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { //Hide the keyboard
                    keyboardController?.hide()
                    emailFocusRequester.requestFocus()
                }
            )
        )
        if (confirmError.value != null) {
            Text(confirmError.value ?: "", color = Color.Red, modifier = Modifier.padding(top = 4.dp))
        }

        // English note: email field
        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                if (it.isNotBlank()) emailError.value = null
            },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocusRequester),
            isError = emailError.value != null,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { //Hide the keyboard
                    keyboardController?.hide()
                }
            )
        )
        if (emailError.value != null) {
            Text(emailError.value ?: "", color = Color.Red, modifier = Modifier.padding(top = 4.dp))
        }

        Button(
            onClick = {
                // reset errors
                usernameError.value = null
                passwordError.value = null
                confirmError.value = null
                emailError.value = null

                // validations
                var valid = true
                if (username.value.isBlank()) {
                    usernameError.value = "Introduce un usuario"
                    valid = false
                }
                if (password.value.isBlank()) {
                    passwordError.value = "Introduce una contraseña"
                    valid = false
                }
                if (confirmPassword.value.isBlank()) {
                    confirmError.value = "Repite la contraseña"
                    valid = false
                }
                if (password.value.isNotBlank() && confirmPassword.value.isNotBlank() && password.value != confirmPassword.value) {
                    passwordError.value = "Las contraseñas no coinciden"
                    confirmError.value = "Las contraseñas no coinciden"
                    valid = false
                }
                if (email.value.isBlank()) {
                    emailError.value = "Introduce un email"
                    valid = false
                }

                if (valid) {
                    // user validation
                    viewModel.register(
                        username = username.value,
                        email = email.value,
                        password = password.value,
                        confirmPassword = confirmPassword.value
                    )
                }
            },
            modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
        ) {
            Text(if (uiState.isLoading) "Registrando..." else "Registrar")
        }

        Button(onClick = onCancel, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Text("Cancelar")
        }
    }
}
