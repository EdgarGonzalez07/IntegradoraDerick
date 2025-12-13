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

    fun register() {
        gyroscope?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun unregister() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        val x = event.values[0]
        val y = event.values[1]

        if (x > 2) viewModel.emitAction(GyroAction.CREATE)
        else if (x < -2) viewModel.emitAction(GyroAction.READ)
        else if (y > 2) viewModel.emitAction(GyroAction.DELETE)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}