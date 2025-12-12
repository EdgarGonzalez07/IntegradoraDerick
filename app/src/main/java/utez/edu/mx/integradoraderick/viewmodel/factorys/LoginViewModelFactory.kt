package utez.edu.mx.integradoraderick.viewmodel.factorys

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import utez.edu.mx.integradoraderick.data.model.usuarios.UsuarioRepository
import utez.edu.mx.integradoraderick.ui.utils.ControladorSesiones
import utez.edu.mx.integradoraderick.viewmodel.LoginViewModel

class LoginViewModelFactory (
    private val repo: UsuarioRepository,
    private val controlador: ControladorSesiones,
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repo, controlador, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}