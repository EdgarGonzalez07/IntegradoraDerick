package utez.edu.mx.integradoraderick.ui.componentes.dialogos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AlertPeru(
    titulo: String,
    mensaje: String,
    onConfirm: () -> Unit,
    confirmText: String,
    onDismiss: () -> Unit,
    dismissText: String,
    showDismiss: Boolean = true,
    content: @Composable (() -> Unit)? = null
){
    Dialog( onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .width(250.dp)
                .background(color = Color.LightGray)
                .height(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titulo,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
                if(mensaje.isNotEmpty()){
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = mensaje,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )
                }
                content?.let {
                    Spacer(modifier = Modifier.height(10.dp))
                    it()
                }

                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .padding(top = 10.dp)
                        .background(Color.Gray)
                ){
                    Text(
                        text = dismissText,
                        color = Color.Black,
                    )
                }
            }

            Box(
                modifier = Modifier.weight(0.3f)
                    .clickable(onClick = onConfirm)
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = confirmText,
                    color = Color.Black
                )
            }
        }
    }
}