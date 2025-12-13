package utez.edu.mx.integradoraderick.data.remote

data class AlmacenRequest(
    val id: Int? = null,
    val nombre: String,
    val ubicacion: String,
    val capacidad: Int,
    val imgUrl: String
)
