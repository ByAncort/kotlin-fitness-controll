package ui.Entrenamiento.Vacio

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class EjercicioItem(
    val id: String,
    var nombre: String = "",
    var series: String = "3",
    var repeticiones: String = "12",
    var peso: String = "0"
)

class EntrenamientoVacioViewModel : ViewModel() {

    var nombreEntrenamiento = mutableStateOf("")
        private set

    var ejercicios = mutableStateOf<List<EjercicioItem>>(emptyList())
        private set

    init {
        // Agrega un ejercicio inicial por defecto
        if (ejercicios.value.isEmpty()) {
            ejercicios.value = listOf(EjercicioItem(id = "1"))
        }
    }

    fun onNombreChange(nuevoNombre: String) {
        nombreEntrenamiento.value = nuevoNombre
    }

    fun agregarEjercicio() {
        val nuevoId = (ejercicios.value.size + 1).toString()
        ejercicios.value = ejercicios.value + EjercicioItem(id = nuevoId)
    }

    fun eliminarEjercicio(id: String) {
        ejercicios.value = ejercicios.value.filter { it.id != id }
    }

    fun actualizarEjercicio(ejercicioActualizado: EjercicioItem) {
        ejercicios.value = ejercicios.value.map {
            if (it.id == ejercicioActualizado.id) ejercicioActualizado else it
        }
    }

    fun guardarEntrenamiento(
        onGuardarEntrenamiento: (String, List<EjercicioItem>) -> Unit
    ) {
        viewModelScope.launch {
            if (nombreEntrenamiento.value.isNotBlank() && ejercicios.value.isNotEmpty()) {
                onGuardarEntrenamiento(nombreEntrenamiento.value, ejercicios.value)
            }
        }
    }
}
