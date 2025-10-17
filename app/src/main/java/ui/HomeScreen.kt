package ui

import AnimatedFitnessIcon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Feature(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val description: String
)

private val featuresList = listOf(
    Feature(Icons.Default.ShowChart, "Progreso", "Visualiza tus avances diarios y semanales"),
    Feature(Icons.Default.PlayArrow, "Entrenamientos", "Accede a tus ejercicios favoritos")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onRecoverClick: () -> Unit
) {
    Scaffold(
        topBar = {},
        content = { innerPadding ->
            HomeContent(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onLoginClick = onLoginClick,
                onRegisterClick = onRegisterClick,
                onRecoverClick = onRecoverClick
            )
        }
    )
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onRecoverClick: () -> Unit
) {
    Box(
        modifier = modifier.background(
            Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f),
                    MaterialTheme.colorScheme.surface
                )
            )
        )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            item {
                // Header con icono y bienvenida
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(24.dp))

                    AnimatedFitnessIcon()

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Bienvenido a FitTracker",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        "Registra y sigue tu progreso en el gimnasio",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            item {
                // Botones de acceso
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = onRegisterClick,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Crear Cuenta", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Text("Iniciar Sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TextButton(onClick = onRecoverClick) {
                        Text("¿Olvidaste tu contraseña?", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }

            // Tarjetas de funciones
            items(featuresList) { feature ->
                FeatureCard(
                    icon = feature.icon,
                    title = feature.title,
                    description = feature.description,
                    onClick = {},
                    containerColor = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun FeatureCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit,
    containerColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(32.dp))
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(description, style = MaterialTheme.typography.bodySmall, lineHeight = 18.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(
                onLoginClick = {},
                onRegisterClick = {},
                onRecoverClick = {}
            )
        }
    }
}
