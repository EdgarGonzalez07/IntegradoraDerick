package utez.edu.mx.integradoraderick.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel
import utez.edu.mx.integradoraderick.viewmodel.GyroAction

class GyroscopeHandler(
    private val sensorManager: SensorManager,
    private val gyroscope: Sensor?,
    private val viewModel: AlmacenViewModel
) : SensorEventListener {

    private val threshold = 2.0f
    private val cooldown = 1200L
    private var lastActionTime = 0L

    fun register() {
        gyroscope?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return

        val now = System.currentTimeMillis()
        if (now - lastActionTime < cooldown) return
        lastActionTime = now

        val x = event.values[0] // Arriba / Abajo
        val y = event.values[1] // Izquierda / Derecha

        when {
            y > threshold -> {
                viewModel.emitAction(GyroAction.CREATE)
            }
            y < -threshold -> {
                viewModel.emitAction(GyroAction.DELETE)
            }
            x > threshold || x < -threshold -> {
                viewModel.emitAction(GyroAction.READ)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
