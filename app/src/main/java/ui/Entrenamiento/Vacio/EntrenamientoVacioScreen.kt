package ui.Entrenamiento.Vacio

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

data class EjercicioItem(
    val id: String,
    var nombre: String = "",
    var series: String = "3",
    var repeticiones: String = "12",
    var peso: String = "0"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntrenamientoVacioScreen(
    onGuardarEntrenamiento: (String, List<EjercicioItem>) -> Unit,
    onCancelar: () -> Unit
) {
    var nombreEntrenamiento by remember { mutableStateOf("") }
    var ejercicios by remember { mutableStateOf(listOf<EjercicioItem>()) }

    // Agregar un ejercicio inicial si está vacío
    LaunchedEffect(Unit) {
        if (ejercicios.isEmpty()) {
            ejercicios = listOf(EjercicioItem(id = "1"))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Entrenamiento") },
                navigationIcon = {
                    IconButton(onClick = onCancelar) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (nombreEntrenamiento.isNotBlank() && ejercicios.isNotEmpty()) {
                                onGuardarEntrenamiento(nombreEntrenamiento, ejercicios)
                            }
                        },
                        enabled = nombreEntrenamiento.isNotBlank() && ejercicios.isNotEmpty()
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Guardar")
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    val nuevoId = (ejercicios.size + 1).toString()
                    ejercicios = ejercicios + EjercicioItem(id = nuevoId)
                },
                icon = { Icon(Icons.Default.Add, contentDescription = "Agregar") },
                text = { Text("Agregar Ejercicio") }
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

            // 📝 Campo para nombre del entrenamiento
            OutlinedTextField(
                value = nombreEntrenamiento,
                onValueChange = { nombreEntrenamiento = it },
                label = { Text("Nombre del entrenamiento") },
                placeholder = { Text("Ej: Rutina de pecho, Entrenamiento full body...") },
                leadingIcon = { Icon(Icons.Default.FitnessCenter, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // 💪 Lista de ejercicios
            Text(
                text = "Ejercicios",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
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
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(
                        items = ejercicios,
                        key = { it.id }
                    ) { ejercicio ->
                        EjercicioCard(
                            ejercicio = ejercicio,
                            onEliminar = {
                                ejercicios = ejercicios.filter { it.id != ejercicio.id }
                            },
                            onEjercicioChange = { ejercicioActualizado ->
                                ejercicios = ejercicios.map {
                                    if (it.id == ejercicioActualizado.id) ejercicioActualizado
                                    else it
                                }
                            }
                        )
                    }
                }
            }

            // 📊 Resumen
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Resumen",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${ejercicios.size} ejercicios agregados",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EjercicioCard(
    ejercicio: EjercicioItem,
    onEliminar: () -> Unit,
    onEjercicioChange: (EjercicioItem) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header con botón eliminar
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Ejercicio ${ejercicio.id}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar ejercicio",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Campo nombre del ejercicio
            OutlinedTextField(
                value = ejercicio.nombre,
                onValueChange = {
                    onEjercicioChange(ejercicio.copy(nombre = it))
                },
                label = { Text("Nombre del ejercicio") },
                placeholder = { Text("Ej: Press de banca, Sentadillas...") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Configuración de series, repeticiones y peso
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Series
                OutlinedTextField(
                    value = ejercicio.series,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            onEjercicioChange(ejercicio.copy(series = it))
                        }
                    },
                    label = { Text("Series") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                // Repeticiones
                OutlinedTextField(
                    value = ejercicio.repeticiones,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() }) {
                            onEjercicioChange(ejercicio.copy(repeticiones = it))
                        }
                    },
                    label = { Text("Reps") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )

                // Peso
                OutlinedTextField(
                    value = ejercicio.peso,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() || char == '.' }) {
                            onEjercicioChange(ejercicio.copy(peso = it))
                        }
                    },
                    label = { Text("Peso (kg)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
            }
        }
    }
}

