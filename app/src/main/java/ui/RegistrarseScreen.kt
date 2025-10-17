package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrarseScreen(
    onBack: () -> Unit,
    onRegistered: () -> Unit,
    vm: RegisterViewModel = viewModel()
) {
    val state by vm.ui.collectAsState()
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    // Navegación reactiva
    LaunchedEffect(state.registered) {
        if (state.registered) onRegistered()
    }

    // Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.message) {
        state.message?.let {
            snackbarHostState.showSnackbar(it)
            vm.messageConsumed()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { inner ->
        Box(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
        ) {
            // Fondo con gradiente sutil
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.background
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Logo/Icono
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🚀",
                            fontSize = 32.sp,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Crear Cuenta",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Comienza tu aventura con nosotros",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                // Formulario de registro
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(24.dp),
                            clip = false
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(32.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Campo Email
                        Column {
                            Text(
                                text = "Correo electrónico",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = state.email,
                                onValueChange = vm::onEmailChange,
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = "Email",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                placeholder = {
                                    Text("tu@email.com")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        // Campo Contraseña
                        Column {
                            Text(
                                text = "Contraseña",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = state.password,
                                onValueChange = vm::onPasswordChange,
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = if (passwordVisible) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Contraseña",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { passwordVisible = !passwordVisible }
                                    ) {
                                        Icon(
                                            imageVector = if (passwordVisible) {
                                                Icons.Default.VisibilityOff
                                            } else {
                                                Icons.Default.Visibility
                                            },
                                            contentDescription = if (passwordVisible) {
                                                "Ocultar contraseña"
                                            } else {
                                                "Mostrar contraseña"
                                            },
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                },
                                placeholder = {
                                    Text("Mínimo 6 caracteres")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        // Campo Confirmar Contraseña
                        Column {
                            Text(
                                text = "Confirmar Contraseña",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            OutlinedTextField(
                                value = state.confirm,
                                onValueChange = vm::onConfirmChange,
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                visualTransformation = if (confirmPasswordVisible) {
                                    VisualTransformation.None
                                } else {
                                    PasswordVisualTransformation()
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Confirmar contraseña",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = { confirmPasswordVisible = !confirmPasswordVisible }
                                    ) {
                                        Icon(
                                            imageVector = if (confirmPasswordVisible) {
                                                Icons.Default.VisibilityOff
                                            } else {
                                                Icons.Default.Visibility
                                            },
                                            contentDescription = if (confirmPasswordVisible) {
                                                "Ocultar contraseña"
                                            } else {
                                                "Mostrar contraseña"
                                            },
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                },
                                placeholder = {
                                    Text("Repite tu contraseña")
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                                ),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }

                        // Indicador de fortaleza de contraseña (opcional)
                        if (state.password.isNotEmpty()) {
                            PasswordStrengthIndicator(password = state.password)
                        }

                        // Mensaje de error
                        if (state.error != null) {
                            Text(
                                text = state.error!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Botón de registro
                        Button(
                            onClick = vm::submit,
                            enabled = !state.loading && state.email.isNotEmpty() && state.password.isNotEmpty() && state.confirm.isNotEmpty(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 0.dp,
                                pressedElevation = 4.dp
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            if (state.loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(20.dp),
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Text(
                                    text = "Crear Cuenta",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        // Términos y condiciones
                        Text(
                            text = "Al registrarte, aceptas nuestros Términos y Condiciones",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Footer - Login
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Ya tienes cuenta? ",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    TextButton(
                        onClick = onBack,
                        modifier = Modifier.padding(start = 4.dp)
                    ) {
                        Text(
                            text = "Inicia Sesión",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Botón de retroceso flotante
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
            ) {
                FloatingActionButton(
                    onClick = onBack,
                    modifier = Modifier.size(56.dp),
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Atrás"
                    )
                }
            }

            // Loading overlay
            if (state.loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .width(140.dp)
                            .height(140.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(32.dp),
                                color = MaterialTheme.colorScheme.primary,
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Creando cuenta...",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

// Componente opcional para mostrar fortaleza de contraseña
@Composable
fun PasswordStrengthIndicator(password: String) {
    val strength = calculatePasswordStrength(password)
    val (color, text) = when (strength) {
        0 -> Pair(MaterialTheme.colorScheme.error, "Débil")
        1 -> Pair(Color(0xFFFFA000), "Media")
        else -> Pair(MaterialTheme.colorScheme.primary, "Fuerte")
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { (strength + 1) / 3f },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = color,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Seguridad: $text",
            style = MaterialTheme.typography.labelSmall,
            color = color
        )
    }
}

fun calculatePasswordStrength(password: String): Int {
    return when {
        password.length < 6 -> 0
        password.length < 8 -> 1
        else -> 2
    }
}