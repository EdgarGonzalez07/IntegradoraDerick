package utez.edu.mx.integradoraderick.data.repository
import retrofit2.Response
import utez.edu.mx.integradoraderick.data.remote.AlmacenRequest
import utez.edu.mx.integradoraderick.data.remote.RetrofitCliente

class AlmacenRepository {

    private val api = RetrofitCliente.api

    suspend fun create(almacen: AlmacenRequest): Response<AlmacenRequest> =
        api.crearAlmacen(almacen)

    suspend fun getAll(): Response<List<AlmacenRequest>> =
        api.getAlmacenes()

    suspend fun update(id: Int, almacen: AlmacenRequest): Response<AlmacenRequest> =
        api.actualizarAlmacen(id, almacen)

    suspend fun delete(id: Int): Response<Void> =
        api.borrarAlmacen(id)
}
