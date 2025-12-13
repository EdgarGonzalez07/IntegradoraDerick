package utez.edu.mx.integradoraderick.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import utez.edu.mx.integradoraderick.ui.componentes.AlmacenAdminCard
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel

@Composable
fun MainScreen(viewModel: AlmacenViewModel) {
    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    // Inicializa y registra el sensor
    LaunchedEffect(Unit) {
        viewModel.initGyroscope(sensorManager, gyroscope)
        viewModel.registerGyro()
        viewModel.loadAlmacenes()
    }

    DisposableEffect(Unit) {
        onDispose { viewModel.unregisterGyro() }
    }

    val almacenes by viewModel.almacenes

    LazyColumn {
        items(almacenes) { almacen ->
            AlmacenAdminCard(
                almacen = almacen,
                onShow = { viewModel.select(it) }
            )
        }
    }
}
