package utez.edu.mx.integradoraderick.data.repository
import okhttp3.MultipartBody
import utez.edu.mx.integradoraderick.data.remote.AlmacenRequest
import utez.edu.mx.integradoraderick.data.remote.RetrofitCliente
class AlmacenRepository {

    private val api = RetrofitCliente.api

    suspend fun create(almacen: AlmacenRequest) =
        api.crearAlmacen(almacen)

    suspend fun getAll() =
        api.getAlmacenes()

    suspend fun delete(id: Int) =
        api.borrarAlmacen(id)

    suspend fun uploadImage(image: MultipartBody.Part) =
        api.uploadImage(image)
}
