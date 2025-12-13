package utez.edu.mx.integradoraderick.data.model.almacenes

data class AlmacenResponse(
    val id: Int,
    val name: String,
    val location: String,
    val capacity: Int,
    val imgUrl: String
)
