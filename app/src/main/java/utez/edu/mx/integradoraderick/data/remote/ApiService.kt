package utez.edu.mx.integradoraderick.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import utez.edu.mx.integradoraderick.data.model.almacenes.Almacen
import utez.edu.mx.integradoraderick.data.model.usuarios.Usuario

interface ApiService {

    @POST("/register")
    suspend fun registrarUsuario(@Body usuario: Usuario): Response<Usuario>

    @POST("/login")
    suspend fun logear(@Body usuario: Usuario): Response<Usuario>

    @PUT("/usuarios/{id}")
    suspend fun actualizarUsuario(@Path("id") id:Int, @Body usuario: Usuario): Response<Usuario>

    @DELETE("/usuarios/{id}")
    suspend fun borrarUsuario(@Path("id") id:Int): Response<Void>

    @POST("/almacenes")
    suspend fun crearAlmacen(@Body almacen: AlmacenRequest): Response<AlmacenRequest>

    @GET("/almacenes")
    suspend fun getAlmacenes(): Response<List<AlmacenRequest>>

    @PUT("/almacenes/{id}")
    suspend fun actualizarAlmacen(@Path("id") id: Int, @Body almacen: AlmacenRequest): Response<AlmacenRequest>

    @DELETE("/almacenes/{id}")
    suspend fun borrarAlmacen(@Path("id") id: Int): Response<Void>

}
