package utez.edu.mx.integradoraderick.ui.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object Asistente {

    fun ServidorDisponible(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected == true
        }
    }

    fun mensajeError(throwable: Throwable): String {
        return when {
            throwable.message?.contains("Unable to resolve host", ignoreCase = true) == true ->
                "No se pudo conectar con el servidor, revisa si esta encendido."
            throwable.message?.contains("Failed to connect", ignoreCase = true) == true ->
                "No se pudo conectar con el servidor, revisa si esta encendido."
            throwable.message?.contains("TimeOut", ignoreCase = true) == true ->
                "El servidor tarda demasiado en responder, intentalo en un rato."
            throwable.message?.contains("Connection refused", ignoreCase = true) == true ->
                "No se pudo conectar con el servidor debido a que se rechazo tu solicitud, revisa si esta encendido"
            throwable is java.net.UnknownHostException ->
                "No se encontro el servidor, revisa la url del servidor"
            throwable is java.net.ConnectException ->
                "No se pudo conectar con el servidor, revisa si esta encendido"
            throwable is java.net.SocketTimeoutException ->
                "El servidor tarda demasiado en responder, intentalo en un rato"
            else -> throwable.message ?: "Error desconocido"
        }
    }

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = withContext(
        Dispatchers.IO) {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Body es nulo"))
                }
            } else {
                Result.failure(Exception("API Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}