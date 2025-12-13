package utez.edu.mx.integradoraderick.ui.screens

import androidx.compose.material3.MaterialTheme
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import utez.edu.mx.integradoraderick.R
import utez.edu.mx.integradoraderick.data.remote.AlmacenRequest
import utez.edu.mx.integradoraderick.ui.componentes.botones.BotonPeru
import utez.edu.mx.integradoraderick.ui.componentes.camposdetexto.CampoDeTextoPeru
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel

@Composable
fun UpdateProductScreen(
    navController: NavController,
    viewModel: AlmacenViewModel
) {
    val almacen = viewModel.selected.value

    // Estados inicializados con los valores actuales del almacén
    var name by remember { mutableStateOf(almacen?.name ?: "") }
    var location by remember { mutableStateOf(almacen?.location ?: "") }
    var capacity by remember { mutableStateOf(almacen?.capacity?.toString() ?: "") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Actualizar producto", fontSize = 35.sp)
        Spacer(modifier = Modifier.height(10.dp))

        // Imagen del almacén
        Box(
            modifier = Modifier
                .size(200.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                AsyncImage(
                    model = almacen?.imgUrl.takeIf { !it.isNullOrBlank() } ?: "",
                    contentDescription = "Imagen del Almacén",
                    placeholder = painterResource(id = R.drawable.agregar),
                    error = painterResource(id = R.drawable.agregar),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Nueva imagen",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))

        CampoDeTextoPeru(name, { name = it }, "Nombre", KeyboardType.Text)
        Spacer(modifier = Modifier.height(10.dp))
        CampoDeTextoPeru(location, { location = it }, "Ubicación", KeyboardType.Text)
        Spacer(modifier = Modifier.height(10.dp))
        CampoDeTextoPeru(capacity, { capacity = it }, "Capacidad", KeyboardType.Number)

        Spacer(modifier = Modifier.height(20.dp))

        BotonPeru(
            text = "Actualizar producto",
            modifier = Modifier.size(180.dp, 50.dp),
            onClick = {
                if (name.isNotEmpty() && location.isNotEmpty() && capacity.isNotEmpty() && almacen != null) {
                    val updatedAlmacen = AlmacenRequest(
                        name = name,
                        location = location,
                        capacity = capacity.toInt(),
                        imgUrl = almacen.imgUrl
                    )
                    viewModel.update(almacen.id, updatedAlmacen) { success ->
                        if (success) {
                            viewModel.loadAlmacenes()
                            navController.popBackStack()
                        } else {
                            errorMessage = "Error al actualizar el producto"
                            showError = true
                        }
                    }
                } else {
                    errorMessage = "LLENA TODOS LOS CAMPOS"
                    showError = true
                }
            }
        )

        if (showError) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}
