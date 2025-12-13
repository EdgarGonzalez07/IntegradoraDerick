package utez.edu.mx.integradoraderick.ui.utils

sealed class Routes(val route: String){
    object Login: Routes("login")
    object Register: Routes("register")
    object Main: Routes("main")
    object NewProduct: Routes("new_product")
}