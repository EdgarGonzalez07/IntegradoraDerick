package utez.edu.mx.integradoraderick.ui.screens

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utez.edu.mx.integradoraderick.R
import utez.edu.mx.integradoraderick.ui.componentes.botones.BotonPeru
import utez.edu.mx.integradoraderick.ui.componentes.camposdetexto.CampoDeTextoPeru

@Composable
fun RegistroScreen(){

    var usuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmarContrasena by remember { mutableStateOf("")}
        Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "REGISTRO",
            fontSize = 35.sp,
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            text = "Bienvenido",
            fontSize = 21.sp,
        )


        Image(
            painter = painterResource(id = R.drawable.almacen),
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

            CampoDeTextoPeru(
                value = usuario,
                onValueChange = { usuario = it },
                label = "Usuario",
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.padding(15.dp))

            CampoDeTextoPeru(
                value = email,
                onValueChange = { email = it },
                label = "Email",
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.padding(15.dp))

            CampoDeTextoPeru(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = "Contraseña",
                keyboardType = KeyboardType.Password
            )

            Spacer(modifier = Modifier.padding(15.dp))

            CampoDeTextoPeru(
                value = confirmarContrasena,
                onValueChange = { confirmarContrasena = it },
                label = "Confirmar Contraseña",
                keyboardType = KeyboardType.Password
            )

        Spacer(modifier = Modifier.padding(15.dp))

        Spacer(modifier = Modifier.padding(10.dp))

        BotonPeru(
            text = "Registrarse",
            onClick = { /* Aquí irá la lógica para agregar el producto */ },
            modifier = Modifier
                .size(width = 180.dp, height = 50.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    RegistroScreen()
}
