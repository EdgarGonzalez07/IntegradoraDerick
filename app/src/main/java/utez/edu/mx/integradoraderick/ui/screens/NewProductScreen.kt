package utez.edu.mx.integradoraderick.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import utez.edu.mx.integradoraderick.R
import utez.edu.mx.integradoraderick.ui.componentes.botones.BotonPeru
import utez.edu.mx.integradoraderick.ui.componentes.camposdetexto.CampoDeTextoPeru

@Composable
fun NewProductScreen(){



    var nombre by remember { mutableStateOf("") }
    var ubicacion by remember { mutableStateOf("") }
    var capacidad by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Agregar nuevo producto",
            fontSize = 35.sp,
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Box(
            modifier = Modifier
                .size(350.dp)
                .clickable {

                    imagePickerLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                Image(
                    painter = painterResource(id = R.drawable.agregar),
                    contentDescription = "Logo",
                    modifier = Modifier.size(size = 200.dp)
                )

            } else {

                AsyncImage(
                    model = imageUri,
                    contentDescription = "Imagen del producto seleccionada",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

        }

        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Toca para seleccionar una imagen", color = Color.Black)
        Spacer(modifier = Modifier.padding(5.dp))

        CampoDeTextoPeru(
            value = nombre,
            onValueChange = { nombre = it },
            label = "Nombre",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.padding(15.dp))

        CampoDeTextoPeru(
            value = ubicacion,
            onValueChange = { ubicacion = it },
            label = "Ubicación",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.padding(15.dp))

        CampoDeTextoPeru(
            value = capacidad,
            onValueChange = { capacidad = it },
            label = "Capacidad",
            keyboardType = KeyboardType.Number
        )
        Spacer(modifier = Modifier.padding(15.dp))


        BotonPeru(
            text = "Agregar producto",
            onClick = { /* Aquí irá la lógica para agregar el producto */ },
            modifier = Modifier
                .size(width = 180.dp, height = 50.dp)
        )


    }
}
