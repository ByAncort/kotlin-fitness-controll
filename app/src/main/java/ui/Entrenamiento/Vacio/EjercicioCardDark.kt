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
fun EjercicioCardDark(
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
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = ejercicio.nombre.ifEmpty { "Ejercicio ${ejercicio.id}" },
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = textPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )

                IconButton(onClick = onEliminar, modifier = Modifier.size(28.dp)) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFef4444)
                    )
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
                shape = RoundedCornerShape(12.dp),
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
                        shape = RoundedCornerShape(12.dp),
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
