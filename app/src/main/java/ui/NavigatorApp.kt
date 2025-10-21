package ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.Entrenamiento.Vacio.EntrenamientoVacioScreen
import ui.Login.LoginScreen
import ui.Principal.PrincipalScreen
import ui.Register.RegistrarseScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigatorApp() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Route.HomeRoot.path) {
        composable(Route.HomeRoot.path) {
            HomeScreen(
                onLoginClick = { nav.navigate(Route.Login.path) },
                onRegisterClick = { nav.navigate(Route.Register.path) },
                onRecoverClick = { nav.navigate(Route.RecoverPassword.path) }
            )
        }
        composable(Route.Login.path) {
            LoginScreen(
                onBack = { nav.popBackStack() },
                onLoginSuccess = {
                    nav.navigate(Route.Principal.path) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Route.Register.path) {
            RegistrarseScreen(
                onBack = { nav.popBackStack() },
                onRegistered = {
                    nav.navigate(Route.Login.path) {
                        popUpTo(Route.HomeRoot.path) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Route.Principal.path) {
            PrincipalScreen(
                onGenerarEntrenamiento = {
                    // Aquí podrías navegar a una pantalla donde se genera el plan con IA
                    // o mostrar un loader mientras el ViewModel procesa
                },
                onEntrenamientoVacio = {
                    nav.navigate(Route.EntrenamientoVacio.path)
                },
                onAbrirRutina = { rutina ->
                    // Aquí podrías abrir una pantalla de detalle de esa rutina
                }

            )
        }
        composable(Route.EntrenamientoVacio.path) {
            EntrenamientoVacioScreen (
                onGuardarEntrenamiento = { nombre, ejercicios ->
                    nav.popBackStack()
                },
                onCancelar = {
                    nav.popBackStack()
                }
            )
        }

    }


}