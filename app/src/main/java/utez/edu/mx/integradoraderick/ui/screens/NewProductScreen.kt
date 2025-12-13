import android.R.attr.password
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun NewProductScreen(
    navController: NavController,
    viewModel: AlmacenViewModel
) {

    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var capacity by remember { mutableStateOf("") }
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

        Text("Agregar nuevo producto", fontSize = 35.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .clickable { imagePickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Image(
                    painter = painterResource(id = R.drawable.agregar),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            } else {
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
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
            text = "Agregar producto",
            modifier = Modifier.size(180.dp, 50.dp),
            onClick = {
                if (name.isNotEmpty() && location.isNotEmpty() && capacity.isNotEmpty()) {
                    viewModel.create(
                        AlmacenRequest(
                            name = name,
                            location = location,
                            capacity = capacity.toInt(),
                            imgUrl = "https://via.placeholder.com/150"
                        )
                    )
                    viewModel.loadAlmacenes()
                    navController.popBackStack()
                } else {
                    errorMessage = "LLENA TODOS LOS CAMPOS"
                    showError = true
                }
            }
        )
    }
}

