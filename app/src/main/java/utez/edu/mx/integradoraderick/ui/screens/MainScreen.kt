package utez.edu.mx.integradoraderick.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import utez.edu.mx.integradoraderick.ui.componentes.AlmacenAdminCard

@Composable
fun MainScreen(viewModel: AlmacenViewModel, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {

        Text(
            text = "PERU WHAREHOUSE",
            fontSize = 25.sp
        )

        Spacer(modifier = Modifier.padding(15.dp))

        val almacen by viewModel.almacen.collectAsStateWithLifecycle()
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(almacen) { almacen ->
                AlmacenAdminCard(
                    almacen = almacen,
                    onShow = {
                        viewModel.clickAlmacen(it)
                        navController.navigate("fullView")
                    }
                )
            }
        }
    }
}