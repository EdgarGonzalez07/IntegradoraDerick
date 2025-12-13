package utez.edu.mx.integradoraderick.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import utez.edu.mx.integradoraderick.R
import utez.edu.mx.integradoraderick.ui.componentes.botones.BotonPeru
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel

@Composable
fun ViewProductScreen(
    viewModel: AlmacenViewModel,
    navController: NavController
) {
    val almacen = viewModel.selected.value

    if (almacen == null) {
        // No se ha seleccionado ningún almacén
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("No se ha seleccionado ningún almacén", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))
            BotonPeru(
                text = "Volver",
                modifier = Modifier.size(180.dp, 50.dp),
                onClick = { navController.popBackStack() }
            )
        }
    } else {
        // Mostrar los detalles del almacén
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Detalle del Almacén", fontSize = 35.sp)
            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = almacen.imgUrl?.takeIf { it.isNotBlank() } ?: R.drawable.agregar,
                contentDescription = "Imagen del Almacén",
                modifier = Modifier
                    .size(250.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text("Nombre: ${almacen.name}", fontSize = 22.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Ubicación: ${almacen.location}", fontSize = 22.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text("Capacidad: ${almacen.capacity}", fontSize = 22.sp)

            Spacer(modifier = Modifier.height(30.dp))
            BotonPeru(
                text = "Volver",
                modifier = Modifier.size(180.dp, 50.dp),
                onClick = { navController.popBackStack() }
            )
        }
    }
}
