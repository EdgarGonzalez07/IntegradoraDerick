package utez.edu.mx.integradoraderick.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import utez.edu.mx.integradoraderick.data.remote.AlmacenRequest
import utez.edu.mx.integradoraderick.data.model.almacenes.AlmacenResponse
import utez.edu.mx.integradoraderick.data.repository.AlmacenRepository
import utez.edu.mx.integradoraderick.sensor.GyroscopeHandler

enum class GyroAction {
    NONE,
    CREATE,
    READ,
    DELETE
}

class AlmacenViewModel(
    private val repository: AlmacenRepository = AlmacenRepository(),
    val context: Context
) : ViewModel() {

    val almacenes = mutableStateOf<List<AlmacenResponse>>(emptyList())
    val selected = mutableStateOf<AlmacenResponse?>(null)

    private val _gyroAction = MutableStateFlow(GyroAction.NONE)
    val gyroAction = _gyroAction.asStateFlow()

    fun loadAlmacenes() {
        viewModelScope.launch {
            try {
                val response = repository.getAll()
                if (response.isSuccessful) {
                    almacenes.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun create(almacen: AlmacenRequest, onResult: (Boolean) -> Unit ) {
        viewModelScope.launch {
            try {
                val response = repository.create(almacen)
                if (response.isSuccessful) {
                    loadAlmacenes()
                    onResult(true)
                }else{
                    onResult(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            try {
                repository.delete(id)
                loadAlmacenes()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun select(almacen: AlmacenResponse) {
        selected.value = almacen
    }

    fun emitAction(action: GyroAction) {
        _gyroAction.value = action
    }

    fun clearAction() {
        _gyroAction.value = GyroAction.NONE
    }

    fun uploadImage(
        imagePart: MultipartBody.Part,
        onResult: (String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = repository.uploadImage(imagePart)
                if (response.isSuccessful) {
                    onResult(response.body()?.url)
                } else {
                    onResult(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(null)
            }
        }
    }

    fun updateAlmacen(updated: AlmacenResponse, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val request = AlmacenRequest(
                    name = updated.name,
                    location = updated.location,
                    capacity = updated.capacity,
                    image = updated.image
                )
                val response = repository.update(updated.id, request)
                if (response.isSuccessful) {
                    // Actualizar la lista local
                    loadAlmacenes()
                    // Actualizar el almacén seleccionado si se está viendo
                    selected.value = updated
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onResult(false)
            }
        }
    }

}
