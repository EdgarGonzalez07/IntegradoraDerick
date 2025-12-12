package utez.edu.mx.integradoraderick.ui.screens

import android.util.Log.e
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import utez.edu.mx.integradoraderick.R
import utez.edu.mx.integradoraderick.ui.componentes.botones.BotonPeru
import utez.edu.mx.integradoraderick.ui.componentes.camposdetexto.CampoDeTextoPeru
import utez.edu.mx.integradoraderick.ui.componentes.dialogos.AlertPeru
import utez.edu.mx.integradoraderick.viewmodel.RegisterUserViewModel

@Composable
fun RegistroScreen(viewModel: RegisterUserViewModel, navController: NavController){

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }

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
                .size(250.dp)
        )

        Spacer(modifier = Modifier.padding(10.dp))

        CampoDeTextoPeru(
            value = name,
            onValueChange = { name = it },
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

        // Campo para la contraseña (con texto oculto)
        CampoDeTextoPeru(
            value = password,
            onValueChange = { password = it },
            label = "Contraseña",
            keyboardType = KeyboardType.Password
        )

        Spacer(modifier = Modifier.padding(15.dp))

        // Campo para confirmar la contraseña
        CampoDeTextoPeru(
            value = password2,
            onValueChange = { password2 = it },
            label = "Confirmar Contraseña",
            keyboardType = KeyboardType.Password
        )


        Spacer(modifier = Modifier.padding(15.dp))

        BotonPeru(
            "Registrar",
            {
                if(name.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()){
                    errorMessage = "Llena todos los campos porfavor."
                    showError = true
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    errorMessage = "Correo electronico invalido."
                    showError = true
                } else if (password != password2){
                    errorMessage = "Las contraseñas no coinciden."
                    showError = true
                } else {
                    viewModel.registrar(name, email, password) { success, error ->
                        if(success){
                            showSuccessDialog = true
                        } else {
                            errorMessage = error ?: "Error al realizar el registro"
                            showError = true
                        }
                    }
                }
            }
        )
    }

    if(showError){
        AlertPeru(
            titulo = "Error",
            mensaje = errorMessage,
            onConfirm = { showError = false },
            confirmText = "Aceptar",
            onDismiss = { showError = false },
            dismissText = ""
        )
    }

    if(showSuccessDialog){
        AlertPeru(
            titulo = "Registro Exitoso",
            mensaje = "Tu cuenta ha sido creada con exito.",
            confirmText = "Ir al login",
            onConfirm = {
                showSuccessDialog = false
                navController.navigate("Login"){
                    popUpTo("Login"){inclusive = true}
                }
            },
            dismissText = "",
            onDismiss = { showSuccessDialog = false
                navController.navigate("Login"){
                    popUpTo("Login"){inclusive = true}
                }
            }
        )
    }

}