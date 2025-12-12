package utez.edu.mx.integradoraderick.ui.componentes

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import coil.compose.AsyncImage
import java.io.File
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utez.edu.mx.integradoraderick.data.model.almacenes.Almacen

@Composable
fun AlmacenAdminCard(
    almacen: Almacen,
    onShow: (Almacen) -> Unit,
    modifier: Modifier = Modifier
){
    val passportBgColor = Color(0xFFF5F8F0)

    Card(modifier = modifier.fillMaxWidth().padding(5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(containerColor = passportBgColor),
        shape = RoundedCornerShape(8.dp)
    ){
        Column (modifier = modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ){
                AsyncImage(
                    model = File(almacen.imgUrl ?: ""),
                    contentDescription = "Imagen de la Prenda",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                )

                Spacer(modifier = modifier.padding(10.dp))

                Column(
                    modifier = modifier.weight(0.8f)
                ){
                    DataField("Nombre:", almacen.nombre.uppercase())
                    DataField("Ubicación:", almacen.ubicacion)
                    DataField("Capacidad:", almacen.capacidad.toString())

                }
            }

            Spacer(modifier = modifier.padding(5.dp))
            Divider(color = Color.Gray.copy(alpha = 0.4f))
            Spacer(modifier = modifier.padding(5.dp))

            Row(){
                Button(
                    onClick = { onShow(almacen) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ){
                    Text("Mostrar mas detalles", color = Color.White)
                }
            }
        }
    }
}



@Composable
fun DataField(
    label: String,
    value: String = "",
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(bottom = 4.dp)) {
        Text(
            text = label.uppercase(),
            fontSize = 9.sp,
            color = Color.Gray,
            letterSpacing = 0.5.sp
        )
        if (value.isNotEmpty()) {
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }
    }
}