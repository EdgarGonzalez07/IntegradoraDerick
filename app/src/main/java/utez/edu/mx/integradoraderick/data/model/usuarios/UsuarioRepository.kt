package utez.edu.mx.integradoraderick.data.model.usuarios

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import utez.edu.mx.integradoraderick.data.remote.RetrofitCliente
import utez.edu.mx.integradoraderick.ui.utils.Asistente

class UsuarioRepository {

    private val api = RetrofitCliente.api

    suspend fun registrar(usuario: Usuario): Result<Usuario> {
        return Asistente.safeApiCall {
            api.registrarUsuario(usuario)
        }
    }

    suspend fun loguear(usuario: Usuario): Result<Usuario> = Asistente.safeApiCall {
        api.logear(usuario)
    }

    suspend fun actualizar(id: Int, usuario: Usuario): Result<Usuario> = Asistente.safeApiCall {
        api.actualizarUsuario(id, usuario)
    }

    suspend fun borrar(id: Int): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = api.borrarUsuario(id)
            if(response.isSuccessful) {
                Result.success(true)
            } else {
                Result.failure(Exception("Error al borrar el usuario: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}