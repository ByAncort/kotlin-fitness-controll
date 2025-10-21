package ui.Principal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalScreen(
    onGenerarEntrenamiento: () -> Unit = {},
    onEntrenamientoVacio: () -> Unit = {},
    onAbrirRutina: (String) -> Unit = {}
) {
    
    val rutinas = listOf("Push-Pull-Legs", "Full Body", "Brazos y Hombros", "Espalda y Pecho")

    
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
                title = { Text("Entrenamientos", color = textPrimary) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = cardColor)
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onEntrenamientoVacio,
                containerColor = accent,
                icon = { Icon(Icons.Default.Add, contentDescription = "Nuevo", tint = Color.White) },
                text = { Text("Nuevo", color = Color.White) }
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

            
            Text(
                text = "Nuevo entrenamiento",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = textPrimary,
                    fontWeight = FontWeight.Bold
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onGenerarEntrenamiento,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = accent)
                ) {
                    Icon(Icons.Default.FitnessCenter, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Generar con IA", color = Color.White)
                }

                OutlinedButton(
                    onClick = onEntrenamientoVacio,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = accent),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 1.dp, brush = androidx.compose.ui.graphics.SolidColor(accent))
                ) {
                    Text("Empezar vacío", color = accent)
                }
            }

            
            Text(
                text = "Rutinas guardadas",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = textPrimary,
                    fontWeight = FontWeight.Bold
                )
            )

            if (rutinas.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes rutinas guardadas aún.", color = textSecondary)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(rutinas) { rutina ->
                        Card(
                            colors = CardDefaults.cardColors(containerColor = cardColor),
                            shape = RoundedCornerShape(12.dp),
                            onClick = { onAbrirRutina(rutina) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = rutina,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = textPrimary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(
                                    text = "Toque para abrir",
                                    style = MaterialTheme.typography.bodySmall.copy(color = textSecondary)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
