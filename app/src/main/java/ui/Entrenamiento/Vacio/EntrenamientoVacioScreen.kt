package ui.Entrenamiento.Vacio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import model.EjercicioItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrenamientoVacioScreen(
    onGuardarEntrenamiento: (String, List<EjercicioItem>) -> Unit = { _, _ -> },
    onCancelar: () -> Unit = {},
    viewModel: EntrenamientoVacioViewModel = viewModel()
) {
    val nombreEntrenamiento by viewModel.nombreEntrenamiento
    val ejercicios by viewModel.ejercicios
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // 🎨 Paleta tipo Tailwind Dark
    val fondo = Brush.verticalGradient(
        listOf(Color(0xFF0f172a), Color(0xFF1e293b))
    )
    val cardColor = Color(0xFF1e293b)
    val borderColor = Color(0xFF334155)
    val textPrimary = Color(0xFFe2e8f0)
    val textSecondary = Color(0xFF94a3b8)
    val accent = Color(0xFF3b82f6)

    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Entrenamiento", color = textPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1e293b)),
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = accent)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.guardarEntrenamientoTemporal()
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Entrenamiento guardado exitosamente 💪",
                                    withDismissAction = true
                                )
                            }
                        },
                        enabled = nombreEntrenamiento.isNotBlank() && ejercicios.isNotEmpty()
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Guardar", tint = accent)
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { viewModel.agregarEjercicio() },
                containerColor = accent,
                icon = { Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White) },
                text = { Text("Agregar Ejercicio", color = Color.White, fontWeight = FontWeight.Bold) },
                shape = RoundedCornerShape(16.dp),
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(fondo)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = nombreEntrenamiento,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre del entrenamiento", color = textSecondary) },
                placeholder = { Text("Ej: Rutina de pecho, Full body...", color = textSecondary) },
                leadingIcon = {
                    Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = accent)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accent,
                    unfocusedBorderColor = borderColor,
                    cursorColor = accent,
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    focusedContainerColor = cardColor,
                    unfocusedContainerColor = cardColor
                )
            )

            Text(
                text = "Ejercicios",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = textPrimary,
                    fontWeight = FontWeight.Bold
                )
            )

            if (ejercicios.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay ejercicios agregados todavía",
                        style = MaterialTheme.typography.bodyMedium.copy(color = textSecondary)
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(items = ejercicios, key = { it.id }) { ejercicio ->
                        EjercicioCardDark(
                            ejercicio = ejercicio,
                            onEliminar = { viewModel.eliminarEjercicio(ejercicio.id) },
                            onEjercicioChange = { viewModel.actualizarEjercicio(it) },
                            cardColor = cardColor,
                            accent = accent,
                            textPrimary = textPrimary,
                            textSecondary = textSecondary,
                            borderColor = borderColor
                        )
                    }
                }
            }
        }
    }
}
