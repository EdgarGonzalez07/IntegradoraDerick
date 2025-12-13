package utez.edu.mx.integradoraderick.data.model.almacenes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "almacenes")
data class Almacen(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val ubication: String,
    val capacity: Int,
    val imgUrl:String
)
