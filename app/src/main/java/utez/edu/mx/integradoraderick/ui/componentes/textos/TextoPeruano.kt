package utez.edu.mx.integradoraderick.ui.componentes.textos

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextoPeruano(text: String){
    Text(text = text, modifier = Modifier.fillMaxWidth(), color = Color.Black, fontSize = 15.sp)
}

@Composable
fun TextoMayorPeruano(text: String){
    Text(text = text, modifier = Modifier.fillMaxWidth(), color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 19.sp)
}

@Composable
fun TituloPeruano(text: String){
    Text(text = text, modifier = Modifier.fillMaxWidth(), color = Color.Black, fontWeight = FontWeight.ExtraBold, fontSize = 30.sp)
}