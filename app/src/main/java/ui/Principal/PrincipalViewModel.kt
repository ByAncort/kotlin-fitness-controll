package ui.Principal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PrincipalUiState(
    val email: String? = "usuario@demo.com",
    val loading: Boolean = false,
    val error: String? = null,
    val loggedOut: Boolean = false
)

class PrincipalViewModel : ViewModel() {

    private val _ui = MutableStateFlow(PrincipalUiState())
    val ui: StateFlow<PrincipalUiState> = _ui.asStateFlow()

    /** 🔄 Simula generar un entrenamiento con IA */
    fun generarEntrenamientoIA() {
        viewModelScope.launch {
            _ui.update { it.copy(loading = true, error = null) }
            delay(1500) // Simula procesamiento
            _ui.update { it.copy(loading = false) }
        }
    }

    /** 🏋️‍♂️ Empieza un entrenamiento vacío */
    fun empezarEntrenamientoVacio() {
        _ui.update { it.copy(error = null) }
        // Aquí podrías navegar o preparar una rutina vacía
    }

    /** 🚪 Cierra sesión */
    fun logout() {
        _ui.update { it.copy(loggedOut = true) }
    }
}
