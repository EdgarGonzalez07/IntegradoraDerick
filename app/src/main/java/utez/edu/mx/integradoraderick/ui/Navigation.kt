package utez.edu.mx.integradoraderick.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import utez.edu.mx.integradoraderick.ui.screens.LoginScreen
import utez.edu.mx.integradoraderick.ui.screens.NewProductScreen
import utez.edu.mx.integradoraderick.ui.screens.RegistroScreen
import utez.edu.mx.integradoraderick.viewmodel.LoginViewModel

@Composable
fun Navigation() {

    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()

    /*AQUI VAN LOS VIEWMODELS QUE NECESITEMOS*/
    //val viewModel: MainViewModel = viewModel()

    val context = LocalContext.current

    //val database = MainDatabase.getDatabase(context)
    //val repository = MainRepository(database.Dao)

    NavHost(navController = navController, startDestination = "NewProduct") {

        composable("Login") { LoginScreen(viewModel = loginViewModel, navController = navController) }
        composable("Register") { RegistroScreen() }
        composable("NewProduct"){ NewProductScreen() }

    }
}