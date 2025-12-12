package utez.edu.mx.integradoraderick.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitCliente {

    private const val BASE_URL = "http://127.0.0.1:5000"

    private val logueando = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val cliente = OkHttpClient.Builder()
        .addInterceptor(logueando)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(cliente)
            .build()
            .create(ApiService::class.java)
    }

}