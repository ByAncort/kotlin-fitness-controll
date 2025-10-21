package model

data class EjercicioItem(
    val id: String,
    var nombre: String = "",
    var series: String = "3",
    var repeticiones: String = "12",
    var peso: String = "0"
)
