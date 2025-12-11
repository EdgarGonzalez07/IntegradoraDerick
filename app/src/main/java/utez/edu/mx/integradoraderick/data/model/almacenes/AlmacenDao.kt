package utez.edu.mx.integradoraderick.data.model.almacenes

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AlmacenDao {

    @Insert
    suspend fun insert(almacen: Almacen)

    @Query("SELECT * FROM almacenes ORDER BY id ASC")
    fun getAllAlmacenes(): Flow<List<Almacen>>

    @Query("SELECT * FROM almacenes WHERE id = :id")
    fun getAlmacenById(id: Int): Flow<Almacen>

    @Update
    suspend fun update(almacen: Almacen)

    @Delete
    suspend fun delete(almacen: Almacen)

}