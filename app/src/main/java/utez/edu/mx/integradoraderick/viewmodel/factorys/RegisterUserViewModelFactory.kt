package utez.edu.mx.integradoraderick.viewmodel.factorys

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import utez.edu.mx.integradoraderick.data.model.usuarios.UsuarioRepository
import utez.edu.mx.integradoraderick.viewmodel.RegisterUserViewModel

class RegisterUserViewModelFactory(
    private val repository: UsuarioRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterUserViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}