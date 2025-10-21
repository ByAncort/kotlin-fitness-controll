package model

import androidx.room.*

@Dao
interface EntrenamientoDao {

    @Insert
    suspend fun insertarEntrenamiento(entrenamiento: EntrenamientoEntity): Long

    @Insert
    suspend fun insertarEjercicios(ejercicios: List<EjercicioEntity>)

    @Transaction
    suspend fun guardarEntrenamientoConEjercicios(
        entrenamiento: EntrenamientoEntity,
        ejercicios: List<EjercicioEntity>
    ) {
        val id = insertarEntrenamiento(entrenamiento).toInt()
        insertarEjercicios(ejercicios.map { it.copy(entrenamientoId = id) })
    }

    @Query("SELECT * FROM entrenamientos")
    suspend fun obtenerEntrenamientos(): List<EntrenamientoEntity>

    @Query("SELECT * FROM ejercicios WHERE entrenamientoId = :id")
    suspend fun obtenerEjerciciosDeEntrenamiento(id: Int): List<EjercicioEntity>

    @Query("DELETE FROM entrenamientos")
    suspend fun limpiarTodo()
}
