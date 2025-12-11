package utez.edu.mx.integradoraderick.data.model.usuarios

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuariosDao {

    @Insert
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuarios")
    fun getAllUsuarios(): Flow<List<Usuario>>

    @Update
    suspend fun update(usuario: Usuario)

    @Delete
    suspend fun delete(usuario: Usuario)

}