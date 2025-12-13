package utez.edu.mx.integradoraderick.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.launch
import utez.edu.mx.integradoraderick.data.model.usuarios.Usuario
import utez.edu.mx.integradoraderick.data.model.usuarios.UsuarioRepository
import utez.edu.mx.integradoraderick.ui.utils.Asistente
import utez.edu.mx.integradoraderick.ui.utils.ControladorSesiones

class LoginViewModel(
    private val repo: UsuarioRepository,
    private val controlador: ControladorSesiones,
    private val context: Context
) : ViewModel() {

    fun logear (email: String, password: String, onResult: (Boolean, String?) -> Unit){
        viewModelScope.launch {
            if(!Asistente.ServidorDisponible(context)) {
                onResult(false, "No hay conexion a internet. Verifica tu conexion.")
                return@launch
            }
            val result =repo.loguear(Usuario(username = "", email = email, password = password))
            if(result.isSuccess){
                val user = result.getOrNull()
                if (user != null) {
                    controlador.guardarUsuario(user.id, user.username, user.email)
                    onResult(true, "Se guardo correctamente")
                } else {
                    onResult(false, "Datos corruptos o dañados.")
                }
            } else {
                val excepcion =result.exceptionOrNull()
                val mensajeError = excepcion?.message ?: ""
                val usermessage = when {
                    mensajeError.contains("401") -> "Correo o la contraseña son incorrectos."
                    mensajeError.contains("400") -> "Falta llenar campos necesarios"
                    mensajeError.contains("404") -> "No se encontro el usuario"
                    excepcion != null -> Asistente.mensajeError(excepcion)
                    else -> "Error al iniciar sesión: $mensajeError, intente nuevamente."
                }
                onResult(false, usermessage)
            }
        }
    }
}

