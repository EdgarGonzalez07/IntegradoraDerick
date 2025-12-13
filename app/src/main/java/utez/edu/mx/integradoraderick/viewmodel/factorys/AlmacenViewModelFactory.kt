package utez.edu.mx.integradoraderick.viewmodel.factorys

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel
import utez.edu.mx.integradoraderick.data.repository.AlmacenRepository
class AlmacenViewModelFactory (
    private val repository: AlmacenRepository,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlmacenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlmacenViewModel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}