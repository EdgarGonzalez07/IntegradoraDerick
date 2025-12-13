package utez.edu.mx.integradoraderick.viewmodel

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import utez.edu.mx.integradoraderick.data.model.almacenes.Almacen
import utez.edu.mx.integradoraderick.data.model.almacenes.AlmacenResponse
import utez.edu.mx.integradoraderick.data.remote.AlmacenRequest
import utez.edu.mx.integradoraderick.data.repository.AlmacenRepository
import utez.edu.mx.integradoraderick.sensor.GyroscopeHandler

enum class GyroAction {
    NONE,
    CREATE,
    READ,
    DELETE
}

class AlmacenViewModel(
    private val repository: AlmacenRepository = AlmacenRepository()
) : ViewModel() {

    val almacenes = mutableStateOf<List<AlmacenResponse>>(emptyList())
    val selected = mutableStateOf<AlmacenResponse?>(null)

    private val _gyroAction = MutableStateFlow(GyroAction.NONE)
    val gyroAction = _gyroAction.asStateFlow()

    fun loadAlmacenes() {
        viewModelScope.launch {
            try {
                val response = repository.getAll()
                println("======== ALMACENES ========")
                println("CODE: ${response.code()}")
                println("BODY: ${response.body()}")
                println("===========================")
                if (response.isSuccessful) {
                    almacenes.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun create(almacen: AlmacenRequest) {
        viewModelScope.launch {
            try {
                val response = repository.create(almacen)
                if (response.isSuccessful) {
                    loadAlmacenes()
                }
            } catch (e: Exception) {
                e.printStackTrace()
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
}


