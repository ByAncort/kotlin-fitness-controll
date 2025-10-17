package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalScreen(
    onGenerarEntrenamiento: () -> Unit = {},
    onEntrenamientoVacio: () -> Unit = {},
    onAbrirRutina: (String) -> Unit = {}
) {
    // 📋 Rutinas de ejemplo (puedes reemplazarlas con datos reales del ViewModel)
    val rutinas = listOf("Push-Pull-Legs", "Full Body", "Brazos y Hombros", "Espalda y Pecho")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Entrenamientos") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // 🧠 Sección: Nuevo Entrenamiento
            Text(
                text = "Nuevo entrenamiento",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onGenerarEntrenamiento,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Generar con IA")
                }

                OutlinedButton(
                    onClick = onEntrenamientoVacio,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Empezar vacío")
                }
            }

            // 💪 Sección: Rutinas guardadas
            Text(
                text = "Rutinas guardadas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (rutinas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes rutinas guardadas aún.")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(rutinas) { rutina ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            onClick = { onAbrirRutina(rutina) }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = rutina,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    text = "Toque para abrir",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
