package utez.edu.mx.integradoraderick.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import utez.edu.mx.integradoraderick.R
import utez.edu.mx.integradoraderick.data.repository.AlmacenRepository
import utez.edu.mx.integradoraderick.ui.componentes.botones.BotonPeru
import utez.edu.mx.integradoraderick.ui.componentes.textos.TextoMayorPeruano
import utez.edu.mx.integradoraderick.ui.componentes.textos.TextoPeruano
import utez.edu.mx.integradoraderick.ui.componentes.textos.TituloPeruano
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel

@Composable
fun AlmacenDetailScreen(
    navController: NavController,
    viewModel: AlmacenViewModel
) {
    val almacen = viewModel.selected.value ?: return
    val cardColor = Color(0xFFF5F8F0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.padding(15.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.padding(end = 50.dp))
            TituloPeruano("Detalles del Almacen")
        }

        Spacer(modifier = Modifier.height(20.dp))

        AsyncImage(
            model = almacen.image.ifBlank { R.drawable.agregar },
            contentDescription = "Imagen del almacén",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(250.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                TextoMayorPeruano("Nombre")
                TextoPeruano(almacen.name)

                Spacer(modifier = Modifier.padding(10.dp))

                TextoMayorPeruano("Ubicación")
                TextoPeruano(almacen.location)

                Spacer(modifier = Modifier.padding(10.dp))

                TextoMayorPeruano("Capacidad")
                TextoPeruano(almacen.capacity.toString())
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        BotonPeru(
            "Volver",
            {
                navController.popBackStack()
            }
        )
    }
}
