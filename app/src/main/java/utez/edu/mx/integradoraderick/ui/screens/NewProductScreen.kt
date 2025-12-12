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

@Composable
fun NewProductScreen(){



    // 6. Estado para guardar el URI (la dirección) de la imagen seleccionada.
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 7. Creamos el "lanzador" que abrirá la galería del teléfono.
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent() // El "contrato" para obtener contenido
    ) { uri: Uri? ->
        // Este bloque se ejecuta cuando el usuario selecciona una imagen (o cierra la galería).
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
        // 8. Contenedor para la imagen. Lo hacemos "clickable" para lanzar la galería.
        Box(
            modifier = Modifier
                .size(350.dp)
                .clickable {
                    // Al hacer clic, lanzamos el selector de imágenes.
                    // "image/*" significa que solo mostrará archivos de imagen.
                    imagePickerLauncher.launch("image/*")
                },
            contentAlignment = Alignment.Center
        ) {
            if (imageUri == null) {
                // Estado inicial: Muestra una imagen por defecto y un texto de ayuda.
                Image(
                    painter = painterResource(id = R.drawable.agregar),
                    contentDescription = "Logo",
                    modifier = Modifier.size(size = 300.dp)
                )

            } else {
                // Estado cuando el usuario ya seleccionó una imagen.
                // 9. Usamos AsyncImage de la librería Coil para mostrar la imagen desde su URI.
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Imagen del producto seleccionada",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop // Para que la imagen se ajuste bien.
                )
            }

        }

        Spacer(modifier = Modifier.padding(5.dp))
        Text(text = "Toca para seleccionar una imagen", color = Color.Black)
        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Nombre") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Ubicacion") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            maxLines = 1,

            )

        Spacer(modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Capacidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            maxLines = 1,
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
