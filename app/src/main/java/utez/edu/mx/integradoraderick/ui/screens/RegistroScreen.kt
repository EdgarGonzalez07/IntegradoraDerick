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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utez.edu.mx.integradoraderick.R

@Composable
fun RegistroScreen(){
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
            fontSize = 20.sp,
        )


        Image(
            painter = painterResource(id = R.drawable.almacen),
            contentDescription = "Logo",
            modifier = Modifier
                .size(250.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Usuario") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            maxLines = 1,
            )

        Spacer(modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = "",
            onValueChange = {},
            placeholder = { Text(text = "Confirmar Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.padding(15.dp))

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {  },
            modifier = Modifier
                .size(width = 140.dp, height = 50.dp)
        ) {
            Text(text = "Registrarse")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroScreenPreview() {
    RegistroScreen()
}
