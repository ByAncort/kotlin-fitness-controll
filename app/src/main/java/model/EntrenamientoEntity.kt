package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entrenamientos")
data class EntrenamientoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String
)

@Entity(tableName = "ejercicios")
data class EjercicioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val entrenamientoId: Int,
    val nombre: String,
    val series: String,
    val repeticiones: String,
    val peso: String
)
