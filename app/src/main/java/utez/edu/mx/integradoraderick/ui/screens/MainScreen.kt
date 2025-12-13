import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import utez.edu.mx.integradoraderick.sensor.GyroscopeHandler
import utez.edu.mx.integradoraderick.ui.componentes.AlmacenAdminCard
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel
import utez.edu.mx.integradoraderick.viewmodel.GyroAction

@Composable
fun MainScreen(
    viewModel: AlmacenViewModel,
    navController: NavController
) {
    val context = LocalContext.current

    val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    val gyroscope =
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    // 👇 Crear el handler UNA sola vez
    val gyroHandler = remember {
        GyroscopeHandler(sensorManager, gyroscope, viewModel)
    }

    // 🔥 Registrar y liberar sensor correctamente
    DisposableEffect(Unit) {

        gyroHandler.register()
        viewModel.loadAlmacenes()

        onDispose {
            gyroHandler.unregister()
        }
    }

    // 🔥 Escuchar acciones del giroscopio
    val gyroAction by viewModel.gyroAction.collectAsState()

    LaunchedEffect(gyroAction) {
        when (gyroAction) {
            GyroAction.CREATE -> {
                navController.navigate("NewProduct")
                viewModel.clearAction()
            }

            GyroAction.READ -> {
                viewModel.loadAlmacenes()
                viewModel.clearAction()
            }

            GyroAction.DELETE -> {
                viewModel.selected.value?.id?.let {
                    viewModel.delete(it)
                }
                viewModel.clearAction()
            }

            else -> {}
        }
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

