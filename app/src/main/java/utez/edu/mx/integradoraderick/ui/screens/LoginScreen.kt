package utez.edu.mx.integradoraderick.ui.screens

import android.util.Log
import android.util.Log.v
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import utez.edu.mx.integradoraderick.R
import utez.edu.mx.integradoraderick.data.remote.RetrofitCliente
import utez.edu.mx.integradoraderick.ui.componentes.botones.BotonPeru
import utez.edu.mx.integradoraderick.ui.componentes.botones.BotonPeruanisimo
import utez.edu.mx.integradoraderick.ui.componentes.dialogos.AlertPeru
import utez.edu.mx.integradoraderick.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel, navController: NavController
){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errormsg by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "PERU WAREHOUSE",
            fontSize = 35.sp,
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            text = "Bienvenido",
            fontSize = 25.sp,
        )


        Image(
            painter = painterResource(id = R.drawable.almacen),
            contentDescription = "Logo",
            modifier = Modifier
                .size(size = 350.dp)

        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it},
            placeholder = { Text(text = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.padding(15.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            placeholder = { Text(text = "Contraseña") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation()
            )

        Spacer(modifier = Modifier.padding(15.dp))

        BotonPeru(
            "Iniciar Sesion",
            {
                if(email.isEmpty() || password.isEmpty()){
                    errormsg = "Llena todos los campos porfavor."
                    showDialog = true
                } else {
                    viewModel.logear(email, password) { success, message ->
                        if(success){
                            navController.navigate("Main") {
                                popUpTo("Login") { inclusive = true}
                            }
                        } else {
                            errormsg = message ?:"Datos no coincidentes"
                            showDialog = true
                        }
                    }
                }
            }
        )

        Spacer(modifier = Modifier.padding(10.dp))

        BotonPeruanisimo(
            "Registrate aqui si no tienes una cuenta.",
            {
                navController.navigate("Register"){
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }

    if(showDialog){
        AlertPeru(
            titulo = "Error",
            mensaje = errormsg,
            onConfirm = { showDialog = false },
            confirmText = "Aceptar",
            onDismiss = { showDialog = false },
            dismissText = ""
        )
    }

}
