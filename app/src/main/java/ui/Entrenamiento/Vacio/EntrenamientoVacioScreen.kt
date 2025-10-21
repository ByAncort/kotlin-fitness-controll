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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrenamientoVacioScreen(
    onGuardarEntrenamiento: (String, List<EjercicioItem>) -> Unit = { _, _ -> },
    onCancelar: () -> Unit = {},
    viewModel: EntrenamientoVacioViewModel = viewModel()
) {
    val nombreEntrenamiento by viewModel.nombreEntrenamiento
    val ejercicios by viewModel.ejercicios

    // 🎨 Paleta tipo Tailwind Dark
    val fondo = Color(0xFF0f172a)
    val cardColor = Color(0xFF1e293b)
    val borderColor = Color(0xFF334155)
    val textPrimary = Color(0xFFe2e8f0)
    val textSecondary = Color(0xFF94a3b8)
    val accent = Color(0xFF3b82f6)

    Scaffold(
        containerColor = fondo,
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Entrenamiento", color = textPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = cardColor),
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = accent)
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.guardarEntrenamiento(onGuardarEntrenamiento) },
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
                text = { Text("Agregar Ejercicio", color = Color.White) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = nombreEntrenamiento,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre del entrenamiento", color = textSecondary) },
                placeholder = { Text("Ej: Rutina de pecho, Full body...", color = textSecondary) },
                leadingIcon = { Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = accent) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accent,
                    unfocusedBorderColor = borderColor,
                    cursorColor = accent,
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    focusedLabelColor = accent,
                    unfocusedLabelColor = textSecondary,
                    focusedContainerColor = cardColor,
                    unfocusedContainerColor = cardColor
                )
            )

            Text(
                text = "Ejercicios",
                style = MaterialTheme.typography.titleMedium.copy(color = textPrimary, fontWeight = FontWeight.Bold)
            )

            if (ejercicios.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No hay ejercicios agregados",
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

            Card(
                colors = CardDefaults.cardColors(containerColor = cardColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Resumen",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = textPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${ejercicios.size} ejercicios agregados",
                        color = textSecondary
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EjercicioCardDark(
    ejercicio: EjercicioItem,
    onEliminar: () -> Unit,
    onEjercicioChange: (EjercicioItem) -> Unit,
    cardColor: Color,
    accent: Color,
    textPrimary: Color,
    textSecondary: Color,
    borderColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Ejercicio ${ejercicio.id}",
                    style = MaterialTheme.typography.titleSmall.copy(color = textPrimary, fontWeight = FontWeight.Bold)
                )
                IconButton(onClick = onEliminar, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color(0xFFef4444))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = ejercicio.nombre,
                onValueChange = { onEjercicioChange(ejercicio.copy(nombre = it)) },
                label = { Text("Nombre del ejercicio", color = textSecondary) },
                placeholder = { Text("Ej: Press de banca, Sentadillas...", color = textSecondary) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accent,
                    unfocusedBorderColor = borderColor,
                    focusedTextColor = textPrimary,
                    unfocusedTextColor = textPrimary,
                    cursorColor = accent,
                    focusedContainerColor = cardColor,
                    unfocusedContainerColor = cardColor
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                listOf(
                    Triple("Series", ejercicio.series) { s: String -> ejercicio.copy(series = s) },
                    Triple("Reps", ejercicio.repeticiones) { s: String -> ejercicio.copy(repeticiones = s) },
                    Triple("Peso (kg)", ejercicio.peso) { s: String -> ejercicio.copy(peso = s) }
                ).forEach { (label, value, update) ->
                    OutlinedTextField(
                        value = value,
                        onValueChange = {
                            if (it.all { c -> c.isDigit() || (label.contains("Peso") && c == '.') })
                                onEjercicioChange(update(it))
                        },
                        label = { Text(label, color = textSecondary) },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = if (label.contains("Peso")) KeyboardType.Decimal else KeyboardType.Number
                        ),
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = accent,
                            unfocusedBorderColor = borderColor,
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            cursorColor = accent,
                            focusedContainerColor = cardColor,
                            unfocusedContainerColor = cardColor
                        )
                    )
                }
            }
        }
    }
}
