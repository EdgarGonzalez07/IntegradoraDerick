package utez.edu.mx.integradoraderick.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import utez.edu.mx.integradoraderick.data.model.usuarios.Usuario
import utez.edu.mx.integradoraderick.data.model.usuarios.UsuarioRepository
import utez.edu.mx.integradoraderick.ui.utils.Asistente

class RegisterUserViewModel(
    private val repo: UsuarioRepository,
    private val context: Context
): ViewModel() {
    fun registrar(username: String, email: String, password: String, onResult: (Boolean, String?) -> Unit){
        viewModelScope.launch {
            if(!Asistente.ServidorDisponible(context)){
                onResult(false, "No hay conexion a internet. Verifica tu conexion.")
                return@launch
            }

            val result = repo.registrar(Usuario(username = username, email = email, password = password))
            if(result.isSuccess){
                onResult(true, null)
            } else {
                val excepcion = result.exceptionOrNull()
                val msj =excepcion?.message ?: ""
                val usermessage = when {
                    msj.contains("409") -> "El correo que ingresaste ya esta registrado."
                    msj.contains("400") -> "Falta llenar campos necesarios."
                    excepcion != null -> Asistente.mensajeError(excepcion)
                    else -> "Error al registrarse: $msj"
                }
                onResult(true,usermessage)
            }
        }
    }
}