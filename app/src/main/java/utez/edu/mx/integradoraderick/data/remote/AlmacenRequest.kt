package utez.edu.mx.integradoraderick.data.remote

data class AlmacenRequest(
    val name: String,
    val location: String,
    val capacity: Int,
    val image: String
)