package utez.edu.mx.integradoraderick.viewmodel

import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import utez.edu.mx.integradoraderick.data.remote.AlmacenRequest
import utez.edu.mx.integradoraderick.data.remote.RetrofitCliente
import utez.edu.mx.integradoraderick.sensor.GyroscopeHandler

class AlmacenViewModel : ViewModel() {

    val almacenes = mutableStateOf<List<AlmacenRequest>>(emptyList())
    val selected = mutableStateOf<AlmacenRequest?>(null)

    private var gyroHandler: GyroscopeHandler? = null

    // Inicializa el sensor desde cualquier Composable
    fun initGyroscope(sensorManager: SensorManager, gyroscope: Sensor?) {
        gyroHandler = GyroscopeHandler(sensorManager, gyroscope, this)
    }

    fun registerGyro() = gyroHandler?.register()
    fun unregisterGyro() = gyroHandler?.unregister()

    // CRUD
    fun loadAlmacenes() {
        viewModelScope.launch {
            try {
                val response = RetrofitCliente.api.getAlmacenes()
                if (response.isSuccessful) {
                    almacenes.value = response.body() ?: emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun select(almacen: AlmacenRequest) {
        selected.value = almacen
    }

    fun create(almacen: AlmacenRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitCliente.api.crearAlmacen(almacen)
                if (response.isSuccessful) {
                    loadAlmacenes()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun update(id: Int, almacen: AlmacenRequest) {
        viewModelScope.launch {
            try {
                val response = RetrofitCliente.api.actualizarAlmacen(id, almacen)
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
                val response = RetrofitCliente.api.borrarAlmacen(id)
                if (response.isSuccessful) {
                    loadAlmacenes()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

