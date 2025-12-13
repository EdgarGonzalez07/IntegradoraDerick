import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import utez.edu.mx.integradoraderick.data.model.usuarios.UsuarioRepository
import utez.edu.mx.integradoraderick.data.repository.AlmacenRepository
import utez.edu.mx.integradoraderick.ui.screens.AlmacenDetailScreen
import utez.edu.mx.integradoraderick.ui.screens.LoginScreen
import utez.edu.mx.integradoraderick.ui.screens.NewProductScreen
import utez.edu.mx.integradoraderick.ui.screens.RegistroScreen
import utez.edu.mx.integradoraderick.ui.utils.ControladorSesiones
import utez.edu.mx.integradoraderick.ui.utils.Routes
import utez.edu.mx.integradoraderick.viewmodel.AlmacenViewModel
import utez.edu.mx.integradoraderick.viewmodel.LoginViewModel
import utez.edu.mx.integradoraderick.viewmodel.RegisterUserViewModel
import utez.edu.mx.integradoraderick.viewmodel.factorys.AlmacenViewModelFactory
import utez.edu.mx.integradoraderick.viewmodel.factorys.LoginViewModelFactory
import utez.edu.mx.integradoraderick.viewmodel.factorys.RegisterUserViewModelFactory

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val controlador = ControladorSesiones(context)

    val userRepo = UsuarioRepository()
    val almacenRepo = AlmacenRepository()

    val logviewModel: LoginViewModel =
        viewModel(factory = LoginViewModelFactory(userRepo, controlador, context))

    val regviewModel: RegisterUserViewModel =
        viewModel(factory = RegisterUserViewModelFactory(userRepo, context))

    val almacenViewModel: AlmacenViewModel =
        viewModel(factory = AlmacenViewModelFactory(almacenRepo, context))

    NavHost(navController = navController, startDestination = Routes.Login.route) {
        composable(Routes.Login.route) { LoginScreen(logviewModel, navController) }
        composable(Routes.Register.route) { RegistroScreen(regviewModel, navController) }
        composable(Routes.Main.route) { MainScreen(viewModel = almacenViewModel, navController = navController) }
        composable(Routes.NewProduct.route) { NewProductScreen(navController = navController, viewModel = almacenViewModel) }
        composable(Routes.Details.route) {
            AlmacenDetailScreen(navController, almacenViewModel)
        }

    }
}
//para comitear
