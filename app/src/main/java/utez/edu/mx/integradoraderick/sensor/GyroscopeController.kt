package utez.edu.mx.integradoraderick.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import utez.edu.mx.integradoraderick.data.remote.AlmacenRequest
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel

class GyroscopeHandler(
    private val sensorManager: SensorManager,
    private val gyroscope: Sensor?,
    private val viewModel: AlmacenViewModel
) : SensorEventListener {

    private val threshold = 2.0f

    fun register() {
        gyroscope?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        val x = event.values[0] // Eje X
        val y = event.values[1] // Eje Y

        // Movimientos
        if (x > threshold) {
            // ARRIBA → Update
            viewModel.selected.value?.let { selected ->
                viewModel.update(selected.id ?: 0, selected)
            }
        } else if (x < -threshold) {
            // ABAJO → Reload
            viewModel.loadAlmacenes()
        }

        if (y > threshold) {
            // DERECHA → Create
            viewModel.create(
                viewModel.selected.value?.copy(id = null) ?: AlmacenRequest(
                    nombre = "Nuevo",
                    ubicacion = "Ubicación",
                    capacidad = 0,
                    imgUrl = ""
                )
            )
        } else if (y < -threshold) {
            // IZQUIERDA → Delete
            viewModel.selected.value?.let { selected ->
                viewModel.delete(selected.id ?: 0)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
