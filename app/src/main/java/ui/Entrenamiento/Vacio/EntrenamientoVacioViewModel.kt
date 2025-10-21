package ui.Entrenamiento.Vacio

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import data.local.EntrenamientoDatabase
import kotlinx.coroutines.launch
import model.EjercicioEntity
import model.EjercicioItem
import model.EntrenamientoEntity

class EntrenamientoVacioViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val db = EntrenamientoDatabase.getInstance(app)
    private val dao = db.entrenamientoDao()

    var nombreEntrenamiento = mutableStateOf("")
        private set

    var ejercicios = mutableStateOf<List<EjercicioItem>>(listOf(EjercicioItem(id = "1")))
        private set

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

    fun guardarEntrenamientoTemporal() {
        viewModelScope.launch {
            if (nombreEntrenamiento.value.isNotBlank()) {
                val entrenamiento = EntrenamientoEntity(nombre = nombreEntrenamiento.value)
                val ejerciciosEntity = ejercicios.value.map {
                    EjercicioEntity(
                        entrenamientoId = 0,
                        nombre = it.nombre,
                        series = it.series,
                        repeticiones = it.repeticiones,
                        peso = it.peso
                    )
                }
                dao.guardarEntrenamientoConEjercicios(entrenamiento, ejerciciosEntity)
            }
        }
    }

    fun obtenerEntrenamientos(onResult: (List<EntrenamientoEntity>) -> Unit) {
        viewModelScope.launch {
            onResult(dao.obtenerEntrenamientos())
        }
    }

    fun limpiarBaseTemporal() {
        viewModelScope.launch {
            dao.limpiarTodo()
        }
    }
}
